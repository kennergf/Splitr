FROM adoptopenjdk/openjdk11
LABEL maintainer="https://github.com/kennergf"
VOLUME [ "/splitr" ]
ADD build/libs/Splitr-0.0.1-SNAPSHOT.jar Splitr.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=prod", "/Splitr.jar" ]