package org.example.demo.spi.service.impl;

import org.example.demo.spi.rest.RestResource;
import org.jboss.logging.Logger;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;


public class SimpleRestProvider implements RealmResourceProvider {
    private static Logger logger = Logger.getLogger(SimpleRestProvider.class);

    private KeycloakSession session;
    public SimpleRestProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new RestResource(session);
    }

    @Override
    public void close() {

    }


}
