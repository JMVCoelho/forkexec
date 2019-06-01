# ForkExec
School Project for Distributed Systems  


## Authors  

Group A40  

| Nome             | N Aluno | gitId       |
|  --------------  | -----   | ---------   |
| Rui Alves        | 65284   | ruialves284 |
| Joao Coelho      | 86448   | JMVCoelho   | 
| Francisco Santos | 87658   | XicaoSantos |


## About the Project

The goal of the project was to develop a system based in SOAP WebServices (JAX-WS), implemented in Java, to manage a platform of food orders.  

The system is divided in 3 main modules:  

1- Hub: Works as an intermediary between users and back-end.   

2- Restaurants: Provides a local server for each restaurant so that its information can be managed.  

3- Points: Mantains accounts and respective balances (in 'pontos-comes').  

There is also a 4th module, CreditCard, that allows the hub to validate credit card details before buying 'pontos-comes'. (This was already implemented)  

An UDDI server was used for restaurants to register themselves and for the Hub to look for restaurants.  


This project was divided in two parts.  
Each part has its own README with more details.


### First Part

The objective of the first part was to implement the system's main modules (hub, rst, and pts).  


### Second Part

The objective of the second part was to make the pts module fault-tolerant, using Quorum Consensus.    
