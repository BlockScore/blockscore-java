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
  <version>4.0.1</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile 'com.blockscore:blockscore-java:4.0.1'
```

### Others

You can download our JAR files from here: https://github.com/BlockScore/blockscore-java/releases

Or, alternatively, build from source by
1. Cloning this repo
2. `./gradlew :build`
3. `./gradlew :jar` (Builds the standard JAR) or `./gradlew :fatJar` (Builds the plug and play jar)

## Usage

```java
import com.blockscore.models.Address;
import com.blockscore.models.Person;
import com.blockscore.net.BlockscoreApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;

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
