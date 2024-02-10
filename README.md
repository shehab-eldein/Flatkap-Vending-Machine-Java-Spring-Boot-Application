# Flatkap Vending Machine Java Spring Boot Application

### Description:
This is a Java Spring Boot application for Flatkap task to simulate a vending machine and expose its rest APIs.

### How to run:
The application is ready to run on intelliJ, Just open the repo on intelliJ and run the App from the main class which is "VendingMachineApplication.class".
The application runs on localhost with port 8080 so make sure this port isn't consumed by any other process or you can chagne the port by updating the application.properties file with:
server.port= any port number

### How to test:
There's a postman collection added inside the repo with name "flat-kap-vending-machine.postman_collection.json" just import it in postman and there'll be all apis needed to test the application.

#### N.B:
Also the app creates some user and product models on startup.
You can list the by calling the following apis:
- For users: get "user/all" api
- For products: get "product/all" api
