package com.wokivovich.tm.service;

import com.wokivovich.tm.entity.User;
import com.wokivovich.tm.repo.UserRepo;
import com.wokivovich.tm.security.UserSecurityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepo repo;

    @Autowired
    public JpaUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserSecurityData loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException("Problem during authentication!");

        User u = repo.findByUsername(username).orElseThrow(s);
        return new UserSecurityData(u);
    }
}
