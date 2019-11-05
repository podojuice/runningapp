package com.server.running.running.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.running.running.dto.Running;

public interface RunningRepository extends JpaRepository<Running, Integer> {

}
