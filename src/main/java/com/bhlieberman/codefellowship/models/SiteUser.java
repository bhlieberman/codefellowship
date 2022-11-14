package com.bhlieberman.codefellowship.models;

import javax.persistence.*;

@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String username;
    private String password;
    public String firstName;
    public String lastName;

    public SiteUser() {}

    public SiteUser(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Object getFirstName() {
        return this.firstName;
    }
}
