package com.parking.parkinglot.entities;

import jakarta.persistence.*;

@Entity

public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;


    private String username;


    private String userGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
