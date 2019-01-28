# Masking SDK [![CircleCI](https://circleci.com/gh/electrumpayments/message-masking-sdk/tree/master.svg?style=shield)](https://circleci.com/gh/electrumpayments/message-masking-sdk/tree/master)

The Masking SDK provides functionality for masking sensitive fields of message models.
A small number of masking classes are provided as well as a base upon which to define
custom masking schemes.

## Getting Started

### Maven

Add the following dependency to your `pom.xml`:
```xml
<dependency>
  <groupId>io.electrum</groupId>
  <artifactId>message-masking-sdk</artifactId>
  <version>2.1.2</version>
</dependency>
```

## Usage

### `@Masked` annotation

To indicate that a field should be masked simply add the `@Masked` annotation:
```java
@Masked
private String sensitiveField;
```

By default the `MaskAll` masker will be used to mask the field, which simply replaces each character with the masking character ('*'):
```
"I am a sensitive value" -> "**********************"
```

To specify which masker should be used to mask a field add the class as a parameter:
```java
@Masked(MaskFull.class)
private String verySensitiveField;
```
