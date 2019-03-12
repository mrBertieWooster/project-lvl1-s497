.DEFAULT_GOAL := compile-run

run:
	java -jar ./target/project-lvl1-s497-1.0-SNAPSHOT-jar-with-dependencies.jar

compile-run: build run

build:
	./mvnw clean package

update:
	./mvnw versions:update-properties versions:display-plugin-updates		

site:
	./mvnw site

pmd:
	./mvnw pmd:pmd