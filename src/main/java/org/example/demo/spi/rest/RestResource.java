package org.example.demo.spi.rest;

import org.example.demo.spi.model.Person;
import org.example.demo.spi.model.RealmModelEntity;
import org.example.demo.spi.service.ServiceProvider;
import org.jboss.logging.Logger;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class RestResource {

    private static Logger logger = Logger.getLogger(RestResource.class);
    private static String sqlQuery = "select user_entity.id,user_entity.email,user_entity.username,keycloak_role.name from user_entity inner join user_role_mapping on user_entity.id = user_role_mapping.user_id inner join keycloak_role on keycloak_role.id=user_role_mapping.role_id";
    private KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

    public RestResource(KeycloakSession session){
        this.session = session;
        this.auth = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();
    }

    private void checkRealmAdmin(String role) {
        logger.infof("Auth details %s", auth);
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (auth.getToken().getRealmAccess() == null || !auth.getToken().getRealmAccess().isUserInRole(role)) {
            throw new ForbiddenException("Does not have the required role");
        }
    }

    @GET
    @Produces("text/plain; charset=utf-8")
    public String getRealmById() {
        String name = session.getContext().getRealm().getDisplayName();
        if (name == null) {
            name = session.getContext().getRealm().getName();
        }
        name = session.getProvider(ServiceProvider.class).sayHello(name);
        logger.infof("get %s",name);
        return "Hello " + name;
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(){
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
        List<RealmModelEntity> userList = em.createNamedQuery("getAllRealms", RealmModelEntity.class).getResultList();
        //List<Realm> realmList = em.createNamedQuery("getAllRealms",Realm.class).getResultList();

        TypedQuery<RealmModelEntity> query = em.createNamedQuery("getAllUsersByRealm", RealmModelEntity.class);
        query.setParameter("realmName", "demo");
        return Response.ok(query.getResultList()).build();
    }

    @GET
    @Path("all-users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        List<Person> persons = new ArrayList<>();
        session.users().getUsersStream(session.getContext().getRealm())
                .forEach(userModel -> {persons.add(new Person(userModel.getUsername(), userModel.getEmail()));});
        return Response.ok(persons).build();
    }

}
