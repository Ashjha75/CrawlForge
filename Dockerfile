# Multi-stage build for CrawlForge
FROM maven:3.9.5-openjdk-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Production stage
FROM tomcat:10.1-jdk21-openjdk

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file from build stage
COPY --from=build /app/target/CrawlForge-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Copy custom server configuration if needed
# COPY server.xml /usr/local/tomcat/conf/

# Create logs directory
RUN mkdir -p /usr/local/tomcat/logs

# Set environment variables
ENV CATALINA_OPTS="-Xmx512m -Xms256m"
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/ || exit 1

# Start Tomcat
CMD ["catalina.sh", "run"]
