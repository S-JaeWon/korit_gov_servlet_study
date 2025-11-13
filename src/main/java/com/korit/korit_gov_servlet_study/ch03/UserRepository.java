package com.korit.korit_gov_servlet_study.ch03;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;
    private List<User> users;
    private Integer userId = 1;


    private UserRepository() {
        users = new ArrayList<>();
    };

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User add(User user) {
        user.setUserId(userId++);
        users.add(user);
        return user;
    }

    public List<User> userList() {
        return users;
    }

    public User searchByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
