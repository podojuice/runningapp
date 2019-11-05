package com.server.running.plan.service;

import java.util.List;

import com.server.running.group.dto.Group;
import com.server.running.plan.dto.GroupPlan;
import com.server.running.plan.dto.UserPlan;
import com.server.running.user.dto.User;

public interface PlanService {
	// 유저 계획 생성
	public UserPlan createUserPlan(UserPlan userPlan);

	// 팀 계획 생성
	public GroupPlan createGroupPlan(GroupPlan groupPlan);

}
