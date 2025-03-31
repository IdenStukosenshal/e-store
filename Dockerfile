FROM amazoncorretto:21-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY target/e-store-0.0.1.jar /app/estore.jar
RUN chown -R appuser:appgroup /app
USER appuser
ENTRYPOINT ["java", "-jar", "estore.jar"]