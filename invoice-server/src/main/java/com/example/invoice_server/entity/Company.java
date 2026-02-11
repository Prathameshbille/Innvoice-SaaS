package com.example.invoice_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;

    private String logoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
