package com.wokivovich.tm.service;

import com.wokivovich.tm.entity.Task;
import com.wokivovich.tm.entity.User;
import com.wokivovich.tm.exception.EntityNotFoundException;
import com.wokivovich.tm.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Can't find this user"));
    }
}
