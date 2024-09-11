package com.kosta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
