build:
	./gradlew clean build

install:
	./gradlew installDist

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

app:
	./gradlew run --args='--spring.profiles.active=development'

clean:
	./gradlew clean

build:
	./gradlew clean build

dev: app

lint:
	./gradlew checkstyleMain checkstyleTest

.PHONY: build