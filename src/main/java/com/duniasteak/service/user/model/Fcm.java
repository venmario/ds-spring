package com.duniasteak.service.user.model;

import com.duniasteak.service.db.DBEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@ToString
@Entity
@Table(name = "FCM", uniqueConstraints = {@UniqueConstraint(columnNames = {"fcm_token"})})
@Getter
@Setter
@NoArgsConstructor
public class Fcm extends DBEntity {

    @Id
    @Column(name = "fcm_token")
    private String fcmToken;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
