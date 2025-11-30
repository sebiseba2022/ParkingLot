package com.parking.parkinglot.common;

import com.parking.parkinglot.entities.UserGroup;

public class UserGroupDto {
    private Long id;
    private String username;
    private String userGroup;

    public UserGroupDto(Long id, String username, String userGroup) {
        this.id = id;
        this.username = username;
        this.userGroup = userGroup;
    }

    public UserGroupDto(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.username = userGroup.getUsername();
        this.userGroup = userGroup.getUserGroup();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserGroup() {
        return userGroup;
    }
}
