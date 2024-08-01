FROM azul/zulu-openjdk:17
COPY ./build/libs/*SNAPSHOT.jar outify.jar
ENTRYPOINT ["java", "-jar", "outify.jar"]