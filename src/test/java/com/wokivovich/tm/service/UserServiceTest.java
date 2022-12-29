package com.wokivovich.tm.service;

import com.wokivovich.tm.exception.EntityNotFoundException;
import com.wokivovich.tm.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService = new UserService(userRepo, passwordEncoder);

    @Test
    void findByUsername_notExistedUsername_NotFoundException() {
        when(userRepo.findByUsername("notExistedUsername")).thenThrow(new EntityNotFoundException("Can't find this user"));

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
            userService.findByUsername("notExistedUsername");
        }).withMessage("Can't find this user");
    }
}
