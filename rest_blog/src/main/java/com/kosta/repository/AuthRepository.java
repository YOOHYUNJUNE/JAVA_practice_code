package com.kosta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.entity.User;

public interface AuthRepository extends JpaRepository<User, Long> {

}
