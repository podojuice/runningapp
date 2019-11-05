package com.server.running.plan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.server.running.plan.dto.GroupPlan;
import com.server.running.plan.dto.UserPlan;
import com.server.running.plan.service.PlanService;

import lombok.extern.slf4j.Slf4j;


/*
 * plan 관련 기능 group에 challenge 형식으로 통합
 */

@Controller
@Slf4j
public class PlanController {
	@Autowired
	private PlanService planService;
	
	// 유저계획 생성
	@PostMapping("/createUserPlan.run")
	public ResponseEntity<UserPlan> createUserPlan(@RequestBody UserPlan userPlan) {
		log.debug("유저 계획 생성 요청");
		return new ResponseEntity<UserPlan>(planService.createUserPlan(userPlan), HttpStatus.OK);
	}
	
	// 팀 계획 생성
	@PostMapping("/createTeamPlan.run")
	public ResponseEntity<GroupPlan> createGroupPlan(@RequestBody GroupPlan groupPlan) {
		log.debug("팀 계획 생성 요청");
		return new ResponseEntity<GroupPlan>(planService.createGroupPlan(groupPlan), HttpStatus.OK);
	}
}
