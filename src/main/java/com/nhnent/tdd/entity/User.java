package com.nhnent.tdd.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hanjin lee
 */

public class User {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
