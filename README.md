# Wit Software Calculator

    
 The program aims mimic calculator basic operations (+ , -, * and /) with 
 only two operatings. RabbitMQ was the technology used to stand as mediator
 between the two modules in the project, the `rest` module and the `calculator` module.

## Requirements

 * Java 8 or superior;
 * Maven;
 * Docker;
 * Logging, Logback and MDC

## Usage

### Running RabbitMQ in Docker container


 First, is need an instance of RabbitMQ running on your `localhost`. Run with Docker 
 using the below command:

    docker run -d --rm --name my-queues -p 15672:15672 -p 5672:5672 rabbitmq:3-management

 After running the command, it will be possible to access the RabbitMQ on your browser
 using the url: `http://localhost:15672`.
        
        username: guest
        password: guest
 
 ### Running the REST API via source code 

 In the root of the source code folder type:
    
    mvn clean install
 
 Then run:

    java -jar rest/target/rest.jar

### Using the available endpoints

#### Adding operation 

    http://localhost:8080/v1/sum?a=3&b=6


#### Subtraction operation

    http://localhost:8080/v1/sub?a=8&b=2


#### Multiplication operation

    http://localhost:8080/v1/mult?a=4&b=4


#### Division operation

    http://localhost:8080/v1/div?a=9&b=3
   