package org.example.demo.spi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "getAllRealms", query = "select r from RealmModelEntity r"),
        @NamedQuery(name = "getAllUsersByRealm", query = "select r from RealmModelEntity r where r.realmName = :realmName"),
        //@NamedQuery(name = "getUserByRealm", query = "select r.realmName, u.userName, u.email, u.roleMappingEntities from RealmModelEntity r JOIN r.users u where r.realmName = :realmName"),
})

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "realm")
public class RealmModelEntity {
    @Id
    @JsonIgnore
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String realmName;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "realm", orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<UserModelEntity> users;
}
