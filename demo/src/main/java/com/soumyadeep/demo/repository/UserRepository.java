package com.soumyadeep.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumyadeep.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
