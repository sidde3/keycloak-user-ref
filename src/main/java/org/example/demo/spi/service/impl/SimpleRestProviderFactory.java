package org.example.demo.spi.service.impl;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;



public class SimpleRestProviderFactory implements RealmResourceProviderFactory {
    private static Logger logger = Logger.getLogger(SimpleRestProviderFactory.class);
    private static String ID = "hello";


    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new SimpleRestProvider(session) ;
    }

    @Override
    public void init(Config.Scope config) {
        logger.infof("%s Initialized...",this.getClass().getName());
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        factory.getSpis().forEach(spi -> {logger.info(spi.getProviderFactoryClass().getName());});
    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ID;
    }
}
