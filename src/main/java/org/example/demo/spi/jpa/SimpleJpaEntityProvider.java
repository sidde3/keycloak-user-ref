package org.example.demo.spi.jpa;

import org.example.demo.spi.model.UserModelEntity;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.models.KeycloakSession;

import java.util.Collections;
import java.util.List;

public class SimpleJpaEntityProvider implements JpaEntityProvider {
    private KeycloakSession session;
    public SimpleJpaEntityProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public List<Class<?>> getEntities() {
        return Collections.singletonList(UserModelEntity.class);
    }

    @Override
    public String getChangelogLocation() {
        return null;
    }

    @Override
    public String getFactoryId() {
        return SimpleJpaEntityProviderFactory.ID;
    }

    @Override
    public void close() {

    }
}
