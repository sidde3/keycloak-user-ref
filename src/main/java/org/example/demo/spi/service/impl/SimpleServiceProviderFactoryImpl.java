package org.example.demo.spi.service.impl;

import org.example.demo.spi.service.ServiceProvider;
import org.example.demo.spi.service.ServiceProviderFactory;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SimpleServiceProviderFactoryImpl implements ServiceProviderFactory {
    private static Logger logger = Logger.getLogger(SimpleServiceProviderFactoryImpl.class);

    @Override
    public ServiceProvider create(KeycloakSession session) {
        return new SimpleServiceProviderImpl();
    }

    @Override
    public void init(Config.Scope config) {
        logger.infof("%s Initialized...",this.getClass().getName());
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "custom-service-provider-impl";
    }
}
