# blockscore-java [![Circle CI](https://circleci.com/gh/BlockScore/blockscore-java/tree/java-4.0.svg?style=shield)](https://circleci.com/gh/BlockScore/blockscore-java/tree/java-4.0)

This is the official library for Java clients of the BlockScore API. [Click here to read the full documentation including code examples](http://docs.blockscore.com/v4.0/java/).

## Requirements

- Java 1.7+

## Installation

### Maven users

### Gradle users

1. Clone this repository
2. `./gradlew build`
3. `./gradlew jar` (Builds the standard JAR) or `./gradlew fatJar` (Builds the plug and play jar)

### Others

You can download our JAR files from here: https://github.com/BlockScore/blockscore-java/releases

## Usage

```java
BlockscoreApiClient client = new BlockscoreApiClient("your api key here");

Address address = new Address();
address.setStreet1("1 Infinite Loop")
       .setStreet2("Apt 6")
       .setCity("Cupertino")
       .setSubdivision("CA")
       .setPostalCode("95014")
       .setCountryCode("US");

SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
Date dateOfBirth = dateOfBirth = formatter.parse("1980-08-23");

Person.Builder person = new Person.Builder(client);
person.setFirstName("John")
      .setMiddleName("Pearce")
      .setLastName("Doe")
      .setDocumentType("ssn")
      .setDocumentValue("0000")
      .setAddress(address)
      .setDateOfBirth(dateOfBirth);

person.create();
```

## Generating javadocs

1. `./gradlew docs`

## Testing

You must have gradle installed. Tests can be run by typing `./gradlew :test`.
