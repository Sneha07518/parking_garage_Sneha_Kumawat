package com.parksmart.entity;

import java.time.Instant;
import jakarta.persistence.*;

@Entity @Table(name = "app_user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String passwordHash;
    @Column(nullable = false) private Instant createdAt;
    public User() {}
    public User(String name, String email, String passwordHash) { this.name=name; this.email=email; this.passwordHash=passwordHash; }
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}