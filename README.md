## blockscore-java [![Circle CI](https://circleci.com/gh/BlockScore/blockscore-java/tree/java-4.0.svg?style=shield)](https://circleci.com/gh/BlockScore/blockscore-java/tree/java-4.0)

An API wrapper for the BlockScore API using Java.

### Requirements

- Java 1.7+

### Installation

#### Maven users

#### Gradle users

1. Clone this repository
2. `./gradlew build`
3. `./gradlew jar` (Builds the standard JAR) or `./gradlew fatJar` (Builds the plug and play jar)

#### Others

You can download our JAR files from here: https://github.com/BlockScore/blockscore-java/releases

### Usage

```java
private static BlockscoreApiClient client = setupBlockscoreApiClient();

Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
Date dateOfBirth = dateOfBirth = formatter.parse("1980-08-23");

Person.Builder builder = new Person.Builder(client);
builder.setFirstName("John")
      .setMiddleName("Pearce")
      .setLastName("Doe")
      .setDocumentType("ssn")
      .setDocumentValue("0000")
      .setAddress(address)
      .setDateOfBirth(dateOfBirth);

builder.create();
```

### Generating javadocs

1. `./gradlew docs`

### Testing

You must have gradle installed. Tests can be run by typing `./gradlew :test`.
