# Guideline how to read this document
Once you have completed the documentation then you can run every application in your local system by configuring system 
prerequisites and don't forget to notice terminal log when you run all application, it helps for better understanding. 
Here is total 3 separate application (3 backend).
- service-registry
- api-gateway
- material-service

# What is microservice?
Microservice is a modern as well as a popular architecture of designing software application over the last few years. 
There are lots of content on the internet to describe what microservice really is and those are very informative. 
But here I wanna describe it simply, concisely in production style.  

A microservice application is consist of different services where every service is an application which is
  1. **Independently deployable**   
  2. **Independently scalable**  

above two are the key requirements of a microservice application.

In this microservice application here are one service **material-service** independently 
deployable and scalable. **It is using a dedicated database but this is not an issue about microservice architecture. 
They can use the same database.**

To expose this service as microservice architecture We used two other service those are **service-registry** for 
service discovery and **api-gateway** for dynamic service routing as well as load balancing.

# Run the services

## System configuration prerequisites
### 1. Clone this project
Open terminal and run
````
git clone https://github.com/lebaz20/e-learning-material-microservice.git
````
In your current directory ``e-learning-material-microservice`` directory will be created with three different project inside.

### 2. Install Java, Maven and Docker
Install java 8 or higher version and Apache Maven 3.6.0 on your system.
Java 11 is installed in my system. This is not an issue. It will work fine in java 8 to java 11.
Install latest Docker.

### 3. RabbitMQ
RabbitMQ is Advanced Message Queuing Protocol (AMQP),
Later you should be able to access it at ``http://localhost:15672/``  
Username: guest  
Password: guest

### 4. Lombok
Lombok plugin should be installed in you IDE otherwise IDE will catch code error. I used Intellij Idea and I had to install 
lombok plugin.

## Package service-registry application
service-registry is the application where all microservice instances will register with a service id. When a service wants 
to call another service, it will ask service-registry by service id to what instances are currently running for that service id. 
service-registry check the instances and pass the request to any active instance dynamically. This is called service 
discovery. The advantage is no need to hard coded instance ip address, service registry always updates itself, if one instance 
goes down, It removes that instance from the registry. 
- **Eureka** is used to achieve this functionality

Open a new terminal and run below command to launch service-registry
````
cd service-registry/
mvn package
````
service-registry will be packaged into a jar file. to be accessed later in http://localhost:8761/  

## Package api-gateway application
api-gateway application is the service for facing other services. Just like the entry point to get any service. Receive 
all the request from user and delegates the request to the appropriate service. 
- **Zuul** is used to achieve this functionality

Open a new terminal and run below command to run api gateway
````
cd api-gateway/
mvn package
````
The application will be accessed later in ``http://localhost:8000/``.

api-gateway is configured such a way that we can call product-service and inventory-service api through api-gateway.   
Like - when we call with a specific url pattern api-gateway will forward the request to the corresponding service based 
on that url pattern.  

| API             | REST Method   | Api-gateway request                                       | Forwarded service   | Forwarded URL                      |
|-----------------|:--------------|:----------------------------------------------------------|:--------------------|:-----------------------------------|
|Get all materials |GET            |``http://localhost:8000/material-service/materials?resourceId={resourceId}&resourceType={resourceType}``         | material-service     | ``http://localhost:8081/materials?resourceId={resourceId}&resourceType={resourceType}`` |    
|Add new material  |POST           |``http://localhost:8000/material-service/materials``     | material-service     | ``http://localhost:8081/materials``      |    
|Edit material     |PUT            |``http://localhost:8000/material-service/materials/{id}``| material-service     | ``http://localhost:8081/materials/{id}`` |    
|Delete material   |DELETE         |``http://localhost:8000/material-service/materials/{id}``| material-service     | ``http://localhost:8081/materials/{id}`` |    
|Get material      |GET            |``http://localhost:8000/material-service/materials/{id`` | material-service     | ``http://localhost:8082/materials/{id}`` |

Above table contains all the used api in this entire application.

## Package material service
Open a new terminal and run below command
````
cd material-service/
mvn package
````
The ``mvn package`` command will create a ``material-service-0.0.1-SNAPSHOT.jar`` inside ``target`` directory. 

Access material-service data source console in browser by
`localhost:8081/h2`  
To connect material data source h2 console use below credentials   
JDBC URL  : `jdbc:h2:~/material`  
User Name : `root`  
Password  : `root`  

## Run application with docker
Open a new terminal and run below command
````
docker-compose up
````
The ``docker-compose up`` command will run all services with the right order as described in ``docker-compose.yml`` inside ``root`` directory. 


Check material table. Right now there is no materials.  
Let's add a new material by calling `localhost:8000/material-service/materials` **POST** endpoint with below body in postman.
````
{
	"url": "test.pdf",
	"resourceId": "1",
	"resourceType": "course"
}
````
