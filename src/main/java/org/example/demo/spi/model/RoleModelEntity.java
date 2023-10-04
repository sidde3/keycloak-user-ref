package org.example.demo.spi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.models.jpa.entities.ClientEntity;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "getAllRoles", query = "select role from UserRoleEntity role"),
        @NamedQuery(name = "getClientRole", query = "select role.id, role.name, client.name from UserRoleEntity role JOIN ClientEntity client"),
        })

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "KEYCLOAK_ROLE")
public class RoleModelEntity {
    @Id
    @JsonIgnore
    @Column(name="ID")
    @Access(AccessType.PROPERTY)
    private String id;

    @Column(name = "NAME")
    private String name;

    @JsonIgnore
    @Column(name = "DESCRIPTION")
    private String description;

    @JsonIgnore
    @Column(name = "REALM_ID")
    private String realmId;

    @Column(name="CLIENT_ROLE")
    private boolean clientRole;

    @JsonIgnore
    @Column(name="CLIENT")
    private String clientId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    @JoinColumn(name = "client", nullable = true)
    private ClientModelEntity clientEntity;

}
