package com.example.se13.model.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

public class User {
    private int id;
    private String userName;
    private String password;
    private Role role;
    private Boolean active;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
