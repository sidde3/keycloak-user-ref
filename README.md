# Sample Keycloak SPIs
This project contains the following
- A HashMap based hardcoded storage provider 
- An attribute based authenticator
- A Custom SPI
- A Rest API Provider

## Build using the following command
````shell
mvn clean package
````

## Deploy this package as a static module. 
````shell
$KEYCLOAK_HOME/bin/jboss-cli.sh --command="module add --name=org.example.demo.spi --resources=target/keycloak-user-info-spi.jar --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,javax.ws.rs.api,org.jboss.logging,org.jboss.resteasy.resteasy-jaxrs"
````

## Configure keycloak to include the SPI
````shell
$KEYCLOAK_HOME/bin/jboss-cli.sh --command="/subsystem=keycloak-server:write-attribute(name=providers,value=[module:org.example.demo.spi])"
````

## Restart keycloak
````shell
$KEYCLOAK_HOME/bin/jboss-cli.sh --command="shutdown --restart=true"
````

## Access the rest API using curl 
````shell
curl http://localhost:8080/auth/realms/master/hello
Hello rh-sso  
````

## Secure resource access 
````shell
curl http://localhost:8080/auth/realms/master/hello/users/admin
{"error":"HTTP 401 Unauthorized"}
````

## Accessing without valid roles.
````shell
curl http://localhost:8080/auth/realms/master/hello/users/admin -H "Authorization: Bearer $token"
{"error":"Does not have the required role"}
````

## Accessing with a valid user roles.
````shell
curl http://localhost:8080/auth/realms/master/hello/users/admin -H "Authorization: Bearer $token"
admin
````

