package org.example.demo.util;

import org.jboss.logging.Logger;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    private static Logger logger = Logger.getLogger(SecurityInterceptor.class);
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());

    private static final ServerResponse PERMISSION_DENIED = new ServerResponse("Access denied for this resource", 403, new Headers<Object>());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        logger.infof("Method: %s, Annotation: %s, SecurityContext %s, URI %s", method.getName(),
                Arrays.toString(method.getDeclaredAnnotations()),
                requestContext.getSecurityContext().getClass().getName(),
                requestContext.getUriInfo().getPath());

        if (requestContext.getUriInfo().getPath().contains("/realms/demo/hello/users")) {

            logger.infof("PATH %s",requestContext.getUriInfo().getAbsolutePath().getPath());

            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            if (authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            if (authorization.get(0).contains(AUTHENTICATION_SCHEME)) {
                logger.infof("Token: %s", authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", ""));
                logger.infof("Is Secure %s",requestContext.getHeaders());
            }

            if(method.isAnnotationPresent(AllowedRoles.class)){
                Annotation annotation = method.getAnnotation(AllowedRoles.class);
                AllowedRoles roles = (AllowedRoles) annotation;
                if(roles.roles() == null || roles.roles().length < 1){
                    requestContext.abortWith(PERMISSION_DENIED);
                    return;
                }
            }
        }





        /*KeycloakSecurityContext sctx = (KeycloakSecurityContext) requestContext.getSecurityContext();
        logger.infof("Security Context: %s", sctx.getToken().getRealmAccess().getRoles());*/

    }

    private void validateHeader() {

    }
}
