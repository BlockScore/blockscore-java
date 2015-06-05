# blockscore-java [![Circle CI](https://circleci.com/gh/BlockScore/blockscore-java.svg?style=shield)](https://circleci.com/gh/BlockScore/blockscore-java)

This is the official library for Java clients of the BlockScore API. [Click here to read the full documentation including code examples](http://docs.blockscore.com/v4.0/java/).

## Requirements

- Java 1.7 and later

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.blockscore</groupId>
  <artifactId>blockscore-java</artifactId>
  <version>4.0.0</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.blockscore:blockscore-java:4.0.0"
```

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

Person.Builder builder = new Person.Builder(client);
builder.setFirstName("John")
       .setMiddleName("Pearce")
       .setLastName("Doe")
       .setDocumentType("ssn")
       .setDocumentValue("0000")
       .setAddress(address)
       .setDateOfBirth(dateOfBirth);

Person person = builder.create();
```

## Generating Javadocs

Enter `./gradlew docs` and a new copy of the Javadocs can be found in `build/docs/javadoc`.

## Testing

You must have gradle installed. Tests can be run by typing `./gradlew :test`.
