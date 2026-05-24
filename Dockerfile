# =========================
# Stage 1: Build
# =========================
FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /app

# Copy only dependency files first for better caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make wrapper executable
RUN chmod +x mvnw

# Download dependencies separately (cached layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Use non-root user
USER spring

EXPOSE 8080

# JVM container optimizations
ENTRYPOINT ["java", \
"-XX:+UseContainerSupport", \
"-XX:MaxRAMPercentage=75.0", \
"-jar", \
"app.jar"]