package com.server.running.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.running.user.dto.User;

public interface UserRepository extends JpaRepository <User, Integer>{

	public Optional<User> findByEmail(String email);

	public List<User> findByEmailContaining(String email);
}
