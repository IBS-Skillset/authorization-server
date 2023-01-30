package com.mystays.authorizationserver.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserAddress {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "type")
    private String type;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "zipcode")
    private String zipcode;
    @ManyToOne(cascade =  CascadeType.MERGE,fetch = FetchType.EAGER)
    @JsonBackReference
    private  User user;
}