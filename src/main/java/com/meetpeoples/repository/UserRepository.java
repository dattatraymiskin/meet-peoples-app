package com.meetpeoples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetpeoples.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
