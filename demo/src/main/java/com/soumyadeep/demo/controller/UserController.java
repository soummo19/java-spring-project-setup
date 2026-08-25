package com.soumyadeep.demo.controller;

import com.soumyadeep.demo.repository.UserRepository;
import com.soumyadeep.demo.service.DemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.soumyadeep.demo.model.User;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final DemoService demoService;

    @GetMapping
    public List<User> getAll() {
        log.info("controller received request to find all users");
        return demoService.getAllUsers();
    }

    // @PostMapping
    // public User create(@RequestBody User user) {
    //     log.info("received request to create user");
    //     return userRepository.save(user);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<User> getById(@PathVariable Long id) {
    //     log.info("received request to get user by id {}", id);
    //     return userRepository.findById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User userDetails) {
    //     log.info("received request to update user by id {}", id);
    //     return userRepository.findById(id)
    //             .map(user -> {
    //                 user.setName(userDetails.getName());
    //                 user.setEmail(userDetails.getEmail());
    //                 return ResponseEntity.ok(userRepository.save(user));
    //             })
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // @GetMapping("health")
    // public ResponseEntity<?> getMethodName() {
    //     log.info("health check");
    //     return ResponseEntity.ok("OK");
    // }
    
}
