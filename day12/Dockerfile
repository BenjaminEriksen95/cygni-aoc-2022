FROM gradle:alpine as BASE
WORKDIR /src
COPY ./ .
RUN gradle shadowJar

FROM openjdk:17-alpine
COPY --from=BASE /src/build/libs/aoc-all.jar /app.jar
COPY input.txt .
CMD java -jar /app.jar
