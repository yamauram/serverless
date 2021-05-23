FROM amazoncorretto:11

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} usr/app/app.jar

WORKDIR /user/app

RUN sh -c 'touch app.jar'

ENTRYPOINT ["sh", "-c", "java -jar ./app.jar" ]