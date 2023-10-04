package org.example.demo.spi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "getAllUsers", query = "select u from SimpleUserEntity u JOIN u.roleMappings r"),
})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_entity")
public class UserModelEntity {
    @Id
    @JsonIgnore
    @Access(AccessType.PROPERTY)
    @Column(name = "ID")
    private String id;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @JsonIgnore
    @Column(name = "REALM_ID")
    private String realm;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<RoleMappingEntity> roleMappingEntities;
}
