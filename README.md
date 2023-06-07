
# Request HDM GM Microservice [ms.requesthdm_gm]  
The purpose of this microservice is to send the fixed orders to the MaxTransit API, through the execution of a recurring batch process.   


How it works  

1. The project includes a properties file in XML format (**properties.xml**), with the entries:  
   `dbhost: The host URL of the PostgreSQL database`  
   `dbport: The database port`  
   `dbuser: The database user`  
   `dbpass: The database password`  
   `dbname: The database name`  
   `urlmtoc: URL of the MTOC-RPO Translator API`  
   `urlmax: URL of the Max Transit API`  
   `timewait: To indicate the maximum timeout value used when hitting the previous APIs`  
   `timelapse: To indicate the lapse of time between executions of the process`  
   
2. The project also includes a **jboss-web.xml** file (located in **src/main/webapp/WEB-INF**) to indicate the *context root path* of the application (that is */olympus/requesthdm_gm/v1*)

2. When the batch process executes, it first retrieves the fixed orders with *envio_flag* equals to *false*, from the *afe_fixed_orders_ev* database table.  

3. Then, the code iterates through the obtained list of fixed orders to recover the color, model and model type of each one.  

4. With that data, it builds and sends a request to the MTOC-RPO API  

5. With the aforementioned response, it then builds and sends a request to the Max Transit API  

6. If the last response was ok, it changes the *envio_flag* field of the corresponding fixed order to *true*  


Tools  

+ Java v1.8.0_202
+ Maven v3.8.6
+ Spring Boot v2.6.14
+ JUnit v5.8.2 
+ Mockito core v4.0.0
+ PostgreSQL driver v42.3.8
+ Lombok v1.18.24
+ Logback v1.2.11
+ Wildfly maven plugin v3.0.2


Run the app

Obtaining the application's WAR file  
`$ mvn clean package`  
  
Running the project as an executable JAR  
`$ mvn spring-boot:run`  

Running the tests  
`$ mvn test`  


Usage  

The microservice **ms.requesthdm_gm** (a batch process) executes automatically every certain amount of time (given in minutes), which is the value given by the *timelapse* variable, included in **properties.xml** file.  

