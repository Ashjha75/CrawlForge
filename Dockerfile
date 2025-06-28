# Multi-stage build for CrawlForge
FROM maven:3.8.3-openjdk-17 AS builder

LABEL maintainer="Ashish Jha <ajha5645@gmail.com>"
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime image with Tomcat
FROM tomcat:10.1-jdk21-openjdk

# Install curl for the healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Remove default webapps for a cleaner deployment
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your compiled WAR file to Tomcat's webapps directory
# Naming it ROOT.war makes it accessible at the root context (e.g., http://your-app.render.com/)
COPY --from=builder /app/target/CrawlForge.war /usr/local/tomcat/webapps/ROOT.war

# Create logs directory
RUN mkdir -p /usr/local/tomcat/logs

# Copy entrypoint script and make it executable
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Set JVM options. These will be picked up by catalina.sh.
ENV CATALINA_OPTS="-Xmx512m -Xms256m"
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

# Expose the default Tomcat port.
# Render will map its assigned PORT to this internal container port.
# However, our entrypoint.sh will dynamically change Tomcat's port if PORT env var is present.
EXPOSE 8080

# Define a health check to verify the application is responsive
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/ || exit 1

# Use the entrypoint script as the container's entry point
ENTRYPOINT ["/entrypoint.sh"]

# Default command to run Tomcat, passed as arguments to the entrypoint script
CMD ["catalina.sh", "run"]