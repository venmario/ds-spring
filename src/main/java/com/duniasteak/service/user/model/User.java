package com.duniasteak.service.user.model;

import com.duniasteak.service.db.IdentityEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email", "phonenumber"})})
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
    private String rolg;
}
