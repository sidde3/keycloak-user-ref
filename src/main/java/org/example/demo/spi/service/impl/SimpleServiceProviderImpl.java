package org.example.demo.spi.service.impl;

import org.example.demo.spi.service.ServiceProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AuthenticationManager;

public class SimpleServiceProviderImpl implements ServiceProvider {

    private KeycloakSession session;
    private AuthenticationManager.AuthResult auth;

    public SimpleServiceProviderImpl() {
/*        this.session = session;
        this.auth = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();*/
    }

    @Override
    public String sayHello(String name) {
        return name;
    }

/*    @Override
    public Boolean securityInitiation(KeycloakSession session) {
        if(auth == null){
            auth = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();
        }

        return auth.getToken().getRealmAccess() == null ? false : true;
    }

    @Override
    public Boolean isUserInRole(KeycloakSession session, String[] roles) {
        if(auth == null){
            auth = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();
        }
        AtomicReference<Boolean> isLoggedIn = new AtomicReference<>(false);
        Arrays.stream(roles).forEach(role -> {
            if(auth.getToken().getRealmAccess().isUserInRole(role)) {
                isLoggedIn.set(true);
                return;
            }
        });

        return isLoggedIn.get();
    }*/

    @Override
    public void close() {

    }

}
