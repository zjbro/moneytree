# runtime
FROM maven:3-openjdk-18 AS builder

# create directory and cd into it
WORKDIR /app

# copying files into the image
COPY pom.xml .
COPY src src
COPY mvnw .
COPY mvnw.cmd .

# build
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:18-oracle

WORKDIR /app

COPY --from=builder /app/target/moneytree-0.0.1-SNAPSHOT.jar moneytree.jar

# environment
ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

ENTRYPOINT java -jar moneytree.jar

