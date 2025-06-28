#!/bin/sh
set -e

# Define the directory where the WAR file will be exploded
WAR_DIR="/usr/local/tomcat/webapps/ROOT"

# Wait for the WAR file to be fully exploded by Tomcat
# This is crucial before attempting to modify files within the exploded WAR (like db.properties)
echo "Waiting for WAR to be exploded in $WAR_DIR..."
while [ ! -d "$WAR_DIR/WEB-INF/classes" ]; do
  sleep 1
done
echo "WAR exploded. Proceeding with configuration."

# Generate db.properties file using environment variables
# This externalizes sensitive database configuration
cat > "$WAR_DIR/WEB-INF/classes/db.properties" <<EOL
db.driver=${DB_DRIVER}
db.url=${DB_URL}
db.username=${DB_USERNAME}
db.password=${DB_PASSWORD}
EOL
echo "db.properties created."

# Dynamically patch server.xml to use the PORT environment variable
# This ensures Tomcat listens on the port expected by Render.
# The default Tomcat server.xml has 'port="8080"'. We replace this with the value of $PORT.
if [ -n "$PORT" ]; then
  echo "PORT environment variable detected: $PORT."
  echo "Patching Tomcat server.xml to listen on port $PORT..."
  # The sed command replaces the default 'port="8080"' with the value of the $PORT env var.
  # It's important that 'port="8080"' exists in the original server.xml for this to work.
  sed -i "s/port=\"8080\"/port=\"$PORT\"/" /usr/local/tomcat/conf/server.xml

  # Basic check to see if the sed command actually made the change
  if ! grep "port=\"$PORT\"" /usr/local/tomcat/conf/server.xml > /dev/null; then
    echo "WARNING: Failed to patch server.xml to use port $PORT. Tomcat might not start on the expected port."
    echo "Please verify the content of /usr/local/tomcat/conf/server.xml after starting the container."
  else
    echo "server.xml patched successfully. Tomcat will listen on port $PORT."
  fi
else
  echo "PORT environment variable not set. Tomcat will use its default configured port (usually 8080)."
  # If PORT is not set, Tomcat should still listen on its default 8080 as per its original server.xml.
  # Render will then map its internal port to this 8080.
fi

# Execute the original command (catalina.sh run) passed to the entrypoint
# This starts the Tomcat server after all configurations are applied.
exec "$@"