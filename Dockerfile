FROM gradle:8.5.0-jdk21

WORKDIR /

COPY / .

RUN ./gradlew installDist

CMD ./build/install/java-project-99/bin/java-project-99