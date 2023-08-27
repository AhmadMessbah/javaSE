package com.example.se13.model.bl;

import com.example.se13.controller.exception.ContentNotFoundException;
import com.example.se13.controller.exception.DuplicateUserNameException;
import com.example.se13.model.da.UserDa;
import com.example.se13.model.entity.User;

import java.util.List;

public class UserBl {
    private static UserBl userBl = new UserBl();

    private UserBl() {
    }

    public static UserBl getUserBl() {
        return userBl;
    }

    public User save(User user) throws Exception {
        try (UserDa da = new UserDa()) {
            if (isValid(user.getUserName())) {
                return da.save(user);
            } else {
                throw new DuplicateUserNameException();
            }
        }
    }

    public User edit(User user) throws Exception {
        try (UserDa da = new UserDa()) {
            if (isValid(user.getUserName())) {
                return da.edit(user);
            } else {
                throw new DuplicateUserNameException();
            }
        }
    }

    public User remove(int id) throws Exception {
        try (UserDa da = new UserDa()) {
            User user = da.findById(id);
            if (user != null) {
                da.remove(id);
                return user;
            }
            if (user == null) {
                throw new ContentNotFoundException();
            }
            return null;
        }
    }


    public User login(String userName, String password) throws Exception {
        try (UserDa da = new UserDa()) {
            return da.findByUserNameAndPassword(userName, password);
        }
    }

    private boolean isValid(String userName) throws Exception {
        try (UserDa da = new UserDa()) {
            return (da.findByUserName(userName) == null) ? true : false;
        }
    }

    public List<User> findAll() throws Exception {
        try (UserDa da = new UserDa()) {
            return da.findAll();
        }
    }
}
