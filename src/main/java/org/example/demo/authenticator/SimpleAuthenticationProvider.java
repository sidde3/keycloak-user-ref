package org.example.demo.authenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.authenticators.browser.AbstractUsernameFormAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.MultivaluedMap;

public class SimpleAuthenticationProvider extends AbstractUsernameFormAuthenticator {
    private static Logger logger = Logger.getLogger(SimpleAuthenticationProvider.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        context.challenge(context.form().setAttribute("realm",context.getRealm()).createLoginTotp());
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        validateNumber(context);
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return !userModel.getFirstAttribute("number").isEmpty();
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    public void validateNumber(AuthenticationFlowContext context) {
        MultivaluedMap<String,String> input = context.getHttpRequest().getDecodedFormParameters();
        String number = input.getFirst("otp");
        logger.infof("Entered Value %s",number);
        logger.infof("Number associated with the user %s",context.getUser().getFirstAttribute("number"));
        if(number.equals(context.getUser().getFirstAttribute("number"))){
            context.success();
        }else {
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,context.form().setAttribute("realm",context.getRealm()).setError("Invalid Number").createLoginTotp());
        }
    }
}
