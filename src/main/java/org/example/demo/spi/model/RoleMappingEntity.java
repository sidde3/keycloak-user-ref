package org.example.demo.spi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name="usersHasRole", query="select u from RoleMappingEntity m, UserEntity u where m.roleId=:roleId and u.id=m.user"),
        @NamedQuery(name="userRoleMapping", query="select m from RoleMappingEntity m where m.user = :user"),
})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER_ROLE_MAPPING")
public class RoleMappingEntity {

    @Id
    @JsonIgnore
    @Column(name = "ROLE_ID")
    private String roleId;

    @JsonIgnore
    @Column(name = "USER_ID")
    private String userId;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = RoleModelEntity.class)
    @JoinColumn(name = "ROLE_ID")
    private RoleModelEntity role;
}
