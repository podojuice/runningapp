package com.server.running.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.plan.dto.GroupPlan;
import com.server.running.plan.dto.UserPlan;
import com.server.running.plan.repository.GroupPlanRepository;
import com.server.running.plan.repository.UserPlanRepository;

@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	private UserPlanRepository userPlanRepository;
	@Autowired
	private GroupPlanRepository groupPlanRepository;
	
	// 유저 계획 생성
	@Override
	public UserPlan createUserPlan(UserPlan userPlan) {
		userPlanRepository.save(userPlan);
		return userPlan;
	}
	
	// 팀 계획 생성
	@Override
	public GroupPlan createGroupPlan(GroupPlan groupPlan) {
		groupPlanRepository.save(groupPlan);
		return groupPlan;
	}
}
