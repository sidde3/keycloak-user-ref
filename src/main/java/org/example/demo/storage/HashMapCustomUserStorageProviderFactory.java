package org.example.demo.storage;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.HashMap;
import java.util.Map;


public class HashMapCustomUserStorageProviderFactory implements UserStorageProviderFactory<HashMapCustomUserStorageProvider> {

    private static Logger logger = Logger.getLogger(HashMapCustomUserStorageProviderFactory.class);
    private static String ID = "test-user-storage-provider";
    private Map<String, String> users;
    private static HashMapCustomUserStorageProvider storageProvider = new HashMapCustomUserStorageProvider();

    @Override
    public HashMapCustomUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        init();
        storageProvider.setModel(componentModel);
        storageProvider.setSession(keycloakSession);
        return storageProvider;
    }

    @Override
    public String getId() {
        return ID;
    }

    public void init() {
        logger.errorf("%s initialized...",this.getClass().getName());
        users = new HashMap<>();
        users.put("sidd", "123");
        users.put("admin", "secret");
        storageProvider.setUsers(users);

    }
}
