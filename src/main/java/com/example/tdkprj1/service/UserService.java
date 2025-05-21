package com.example.tdkprj1.service;

import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<TbUser> getUserById(long userId) {
        return userRepository.findById(userId);
    }
}