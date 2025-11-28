package org.example.bet.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bet.models.enums.UserRole;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "successful_predictions", nullable = false)
    private int successfulPredictions = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

}