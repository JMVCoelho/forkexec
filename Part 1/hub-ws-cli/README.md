# Hub Web Service client

## Authors

Group A40

### Lead developer 

Joao Coelho 86448 JMVCoelho

### Contributors

Rui Alves 65284 ruialves284

Franciso Santos 87658 XicaoSantos

## About

This is a Java Web Service client

The client uses the wsimport tool (included with the JDK since version 6)
to generate classes that can invoke the web service and
perform the Java to XML data conversion.

The client needs access to the WSDL file,
either using HTTP or using the local file system.


## Instructions for using Maven

You must start jUDDI and server first.

The default WSDL file location is ${basedir}/src/wsdl .
The WSDL URL location can be specified in pom.xml
/project/build/plugins/plugin[artifactId="jaxws-maven-plugin"]/configuration/wsdlUrls

The jaxws-maven-plugin is run at the "generate-sources" Maven phase (which is before the compile phase).

To generate stubs using wsimport:

```
mvn generate-sources
```

To compile:

```
mvn compile
```

To run using exec plugin:

```
mvn exec:java
```

## To run Integration Tests

```
mvn verify
``` 

## To configure the Maven project in Eclipse

'File', 'Import...', 'Maven'-'Existing Maven Projects'

'Select root directory' and 'Browse' to the project base folder.

Check that the desired POM is selected and 'Finish'.


----
