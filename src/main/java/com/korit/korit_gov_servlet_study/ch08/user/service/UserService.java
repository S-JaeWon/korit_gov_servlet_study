package com.korit.korit_gov_servlet_study.ch08.user.service;

import com.korit.korit_gov_servlet_study.ch08.user.dao.UserDao;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;

import java.util.Optional;

public class UserService {
    private static UserService instance;
    private UserDao userDao;

    private UserService() {
        userDao = UserDao.getInstance();
    };

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean checkUsername(String username) {
        Optional<User> searchUser = userDao.searchUsername(username);
        return searchUser.isPresent();
    }

    public User addUser(SignupReqDto signupReqDto) {
        return userDao.add(signupReqDto.toEntity());
    }
}
