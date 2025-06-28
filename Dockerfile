# Multi-stage build for CrawlForge
FROM maven:3.8.3-openjdk-17 AS builder

LABEL maintainer="Ashish Jha <ajha5645@gmail.com>"
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk21-openjdk

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=builder /app/target/CrawlForge.war /usr/local/tomcat/webapps/ROOT.war

RUN mkdir -p /usr/local/tomcat/logs

# Add entrypoint script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENV CATALINA_OPTS="-Xmx512m -Xms256m"
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/ || exit 1

ENTRYPOINT ["/entrypoint.sh"]
CMD ["catalina.sh", "run"]