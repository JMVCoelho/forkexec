# ForkExec

**Fork**: delicious menus for **Exec**utives

Distributed Systems 2018-2019, 2nd semester project

## Authors

Group A40

| Nome             | N Aluno | gitId       |
|  --------------  | -----   | ---------   |
| Rui Alves        | 65284   | ruialves284 |
| Joao Coelho      | 86448   | JMVCoelho   | 
| Francisco Santos | 87658   | XicaoSantos |


## Getting Started

The overall system is composed of multiple services and clients.
The main service is the _hub_ service that is aided by the _pts_ service. 
There are also multiple _rst_ services, one for each participating restaurant.

See the project statement for a full description of the domain and the system.



### Prerequisites

Java Developer Kit 8 is required running on Linux, Windows or Mac.
Maven 3 is also required.

To confirm that you have them installed, open a terminal and type:

```
javac -version

mvn -version
```


### Installing

To compile and install all modules:

```
mvn clean install -DskipTests
```

The tests are skipped because they require each server to be running.

### Run Servers

2 restaurant servers:
```
cd rst-ws 
mvn compile exec:java
```
(other bash)
```
cd rst-ws 
mvn compile exec:java -Dws.i=2
```

1 points server:
```
cd pts-ws
mvn compile exec:java
```
1 hub server:
```
cd hub-ws
mvn compile exec:java
```

##Run Tests
Once servers are up, from root directory:

```
mvn verify
```


## Built With

* [Maven](https://maven.apache.org/) - Build Tool and Dependency Management
* [JAX-WS](https://javaee.github.io/metro-jax-ws/) - SOAP Web Services implementation for Java


## Versioning

We use [SemVer](http://semver.org/) for versioning. 






