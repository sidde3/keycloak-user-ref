package org.example.demo.spi.jpa;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;


public class SimpleJpaEntityProviderFactory implements JpaEntityProviderFactory{
    static String ID = "simple-jpa-provider-factory";
    private static Logger logger = Logger.getLogger(SimpleJpaEntityProviderFactory.class);

    @Override
    public JpaEntityProvider create(KeycloakSession session) {
        return new SimpleJpaEntityProvider(session);
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        logger.infof("%s initialized...", this.getClass().getName());
    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ID;
    }
}
