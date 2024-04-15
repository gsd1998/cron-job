FROM openjdk:17
EXPOSE 8181
COPY target/*.jar spring-cron-job.jar
ENTRYPOINT ["java", "-jar", "spring-cron-job.jar"]
