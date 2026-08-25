package com.soumyadeep.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soumyadeep.demo.model.User;
import com.soumyadeep.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        log.info("service method 1 received request to find all users");
        return getAllUsers2();
    }

    private List<User> getAllUsers2() {
        log.info("service method 2 received request to find all users");
        return userRepository.findAll();
    }

}
