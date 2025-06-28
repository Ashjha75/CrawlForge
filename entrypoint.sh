#!/bin/sh
set -e

# Wait for WAR to be exploded
WAR_DIR="/usr/local/tomcat/webapps/ROOT"
while [ ! -d "$WAR_DIR/WEB-INF/classes" ]; do
  sleep 1
done

cat > "$WAR_DIR/WEB-INF/classes/db.properties" <<EOL
db.driver=${DB_DRIVER}
db.url=${DB_URL}
db.username=${DB_USERNAME}
db.password=${DB_PASSWORD}
EOL

exec "$@"