package org.example.demo.storage;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.LegacyUserCredentialManager;
import org.keycloak.models.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.util.*;
import java.util.stream.Stream;

public class HashMapCustomUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
        UserQueryProvider {
    private Logger logger = Logger.getLogger(HashMapCustomUserStorageProvider.class);
    private ComponentModel model;
    private KeycloakSession session;
    private Map<String, String> users;

    public void setModel(ComponentModel model) {
        this.model = model;
    }

    public void setSession(KeycloakSession session) {
        this.session = session;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }


    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        logger.errorf("getUserById: %s", id);
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();
        return getUserByUsername(realm,username);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        logger.errorf("getUserByUsername: %s", username);
        return users.get(username).isEmpty() ? null : createUserModel(realm, username);
    }

    private UserModel createUserModel(RealmModel realm, String username){
        return new AbstractUserAdapter(session, realm, model){

            @Override
            public String getUsername() {
                return username;
            }
            @Override
            public SubjectCredentialManager credentialManager() {
                return new LegacyUserCredentialManager(session,realm,this);
            }
        };
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        return getUserByUsername(realm,email);
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return credentialType.equals(PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType) && !users.get(user.getUsername()).isEmpty();
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        UserCredentialModel cred = (UserCredentialModel) credentialInput;
        logger.infof("Input Credential: %s; Actual Credential %s",cred.getValue(),users.get(user.getUsername()));
        return users.get(user.getUsername()).equals(cred.getValue());
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        List<UserModel> userModelList = new LinkedList<>();
        users.forEach((username, password) -> {
            if(username.contains(search)){
                userModelList.add(createUserModel(realm,username));
            }
        });

        return userModelList.stream();
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        return null;
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return null;
    }
}

