package com.server.running.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.running.plan.dto.UserPlan;

public interface UserPlanRepository extends JpaRepository<UserPlan, Integer> {

}
