package com.duniasteak.service.user.model;

import com.duniasteak.service.db.IdentityEntity;
import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email", "phonenumber"})})
@Getter
@Setter
@NoArgsConstructor
public class User extends IdentityEntity {

    @Column(name = "username", length = 191)
    private String username;

    @Column(name = "firstname", length = 191)
    private String firstname;

    @Column(name = "lastname", length = 191)
    private String lastname;

    @Column(name = "phonenumber", length = 191)
    private String phonenumber;

    @Column(name = "email", length = 191)
    private String email;

    @Column(name = "password", length = 191)
    private String password;

    @Column(name = "poin")
    private Long poin;

    @Column(name = "role", length = 191)
    private String role;

    @PrePersist
    public void prePersist() {
        if (poin == null) {
            poin = 0L;
        }
        if (role == null) {
            role = "customer";
        }
    }
}
