package com.korit.korit_gov_servlet_study.ch07;

import java.util.List;

public class UserService {
    private static UserService instance;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(UserRepository.getInstance());
        }
        return instance;
    }

    public boolean isDupleUsername(String username) {
        User searchUsername = userRepository.searchByUsername(username);
        return searchUsername != null;
    }

    public User addUser(SignupReqDto signupReqDto) {
        User user = signupReqDto.toEntity();
        return userRepository.add(user);
    }


    public List<User> userGetAll() {
        return userRepository.userListAll();
    }

    public User userList(String username) {
        return userRepository.searchByUsername(username);
    }



}
