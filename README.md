# Round-Robin Load Balancing at API Gateway

A runnable three-project Spring Boot example using:
- Java 17
- Spring Boot 3.4.5
- Spring Cloud 2024.0.1
- Netflix Eureka for service discovery
- Spring Cloud Gateway + Spring Cloud LoadBalancer for routing

## Project structure

```text
round-robin-api-gateway/
├── eureka-server/
├── driver-service/
└── api-gateway/
```

## Prerequisites

1. JDK 17 installed and `JAVA_HOME` configured.
2. Maven 3.8+ installed (`mvn -version` should work).
3. Internet connection for the first Maven dependency download.

## Run the projects (Windows)

Open four terminals in this extracted folder.

### Terminal 1: Eureka Server

```powershell
cd eureka-server
mvn spring-boot:run
```

Wait until it starts, then open http://localhost:8761.

### Terminals 2, 3 and 4: Three Driver Service instances

Run each command from the `driver-service` folder in a separate terminal:

```powershell
cd driver-service
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

```powershell
cd driver-service
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8082"
```

```powershell
cd driver-service
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"
```

Wait until all three instances appear under `DRIVER-SERVICE` in Eureka.

### Terminal 5: API Gateway

```powershell
cd api-gateway
mvn spring-boot:run
```

The Gateway listens on port 8080.

## Test

Send repeated GET requests to:

```text
http://localhost:8080/drivers/test
```

The response includes the port of the Driver Service instance that handled the request.
With all instances registered and healthy, requests should rotate among ports 8081, 8082 and 8083.

Direct instance checks:

- http://localhost:8081/drivers/test
- http://localhost:8082/drivers/test
- http://localhost:8083/drivers/test

Eureka dashboard:

- http://localhost:8761

## Notes

- Start Eureka first, then all three Driver Service instances, then the Gateway.
- Eureka registration and failure detection are not instantaneous. After stopping an instance, allow time for Eureka to detect the failure before testing again.
- The first Maven run may take several minutes while dependencies download.
- This sample uses the reactive Spring Cloud Gateway starter. Do not add `spring-boot-starter-web` to the Gateway project.
- The default Spring Cloud LoadBalancer implementation uses round-robin selection. The first selected port can vary depending on service registration order.

## If a port is already occupied

Change the port in the run command, for example:

```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8084"
```
