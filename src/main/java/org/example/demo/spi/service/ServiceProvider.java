package org.example.demo.spi.service;

import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;

public interface ServiceProvider extends Provider {
    String sayHello(String name);
/*    Boolean securityInitiation(KeycloakSession session);
    Boolean isUserInRole(KeycloakSession session, String[] roles);*/
}
