from bellsoft/liberica-openjdk-alpine:24.0.1 as builder
workdir /app
copy . .
run ./mvnw clean package -DskipTests

from bellsoft/liberica-openjre-alpine:24.0.1
workdir /app
copy --from=builder /app/target/*.jar app.jar
expose 80
entrypoint ["java", "-jar", "app.jar"]