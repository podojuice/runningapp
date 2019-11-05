package com.server.running.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.running.plan.dto.GroupPlan;

public interface GroupPlanRepository extends JpaRepository<GroupPlan, Integer> {

}
