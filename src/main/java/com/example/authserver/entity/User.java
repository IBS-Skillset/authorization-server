package com.example.authserver.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(length = 60)
    private String password;
    private String email;
    private String role;
    private boolean enabled = true;
}
