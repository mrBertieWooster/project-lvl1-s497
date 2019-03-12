.DEFAULT_GOAL := compile-run

run:
	java -jar ./target/project-lvl1-s497-1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	rm -rf ./target

compile: clean
	mkdir -p ./target/classes
	javac -d ./target/classes ./src/main/java/games/Slot.java

compile-run: build run

build: compile
	./mvnw clean package

update:
	./mvnw versions:update-properties
	./mvnw versions:display-plugin-updates

site:
	./mvnw site

pmd:
	./mvnw pmd:pmd