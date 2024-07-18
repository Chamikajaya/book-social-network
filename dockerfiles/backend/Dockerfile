# todo: COMPLETE THE DEPLOYMENT TO VPS  + INTERGRATE SLACK NOTIFICATION

# * If running docker build inside the backend dir --> docker build -t cjbro/books-project:1.0.0 -f ..\dockerfiles\backend\Dockerfile .

# Build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline  # download dependencies
COPY src ./src
RUN mvn clean package -DskipTests


# Runtime stage
FROM amazoncorretto:17

# command line arguments , if not provided the following is the default
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
# copy the jar file which was built in the previous stage -->
COPY --from=build /build/target/books-project-* /app
EXPOSE 8080

ENV JAR_VERSION=${APP_VERSION}
ENV SPRING_PROFILES_ACTIVE=${PROFILE}

ENV DATABASE_URL=jdbc:postgresql://psql_container_books_project:5432/books-network


CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Dspring.datasource.url=${DATABASE_URL} books-project-${JAR_VERSION}.jar





