blockscore-java
===============

An API wrapper for the BlockScore API using Java.

Latest Revision: 3.0 <br />
API Compatibility: 3.0

##System Requirements (For Building)
1. Java 1.7+

##How to build
1. Clone this repository
2. `./gradlew build`
3. `./gradlew jar` (Builds the standard JAR) or `./gradlew fatJar` (Builds the plug and play jar)

##How to run code quality tools
1. Checkstyle (Ensures code style) `./gradlew checkstyleMain`
2. PMD (Checks for bugs) `./gradlew pmdMain`
3. Findbugs (Checks for bugs) `./gradlew findbugsMain`

##Dependencies Required (If using standard JAR)
1. Retrofit 1.6.1+
2. Retrofit Converter-Jackson 1.6.1+
3. RxJava 1.0+
4. OkHTTP 2.0+
5. OkHTTP UrlConnection 2.0+