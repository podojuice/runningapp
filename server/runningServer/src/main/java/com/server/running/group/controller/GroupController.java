package com.server.running.group.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.Join;
import com.server.running.group.dto.UserGroup;
import com.server.running.group.service.GroupService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	// 그룹 생성
	@PostMapping("/createTeam.run")
	public ResponseEntity<Boolean> createTeam(@RequestBody UserGroup userGroup) {
		log.debug("그룹 생성 요청");
		return new ResponseEntity<Boolean>(groupService.createTeam(userGroup), HttpStatus.OK);
	}
	
	// 그룹 수정
	@PutMapping("/updateTeam.run")
	public ResponseEntity<Boolean> updateTeam(@RequestBody Group group) {
		log.debug("그룹 수정 요청");
		return new ResponseEntity<Boolean>(groupService.updateTeam(group), HttpStatus.OK);
	}
	
	// 그룹 삭제
	@DeleteMapping("/deleteTeam.run")
	public ResponseEntity<Boolean> deleteTeam(@RequestBody Group group) {
		log.debug("그룹 삭제 요청");
		return new ResponseEntity<Boolean>(groupService.deleteTeam(group), HttpStatus.OK);
	}
	
	// 그룹에 참가
	@PostMapping("/joinTeam.run")
	public ResponseEntity<Boolean> joinTeam(@RequestBody Join join){
		log.debug("그룹 참가 요청");
		return new ResponseEntity<Boolean>(groupService.joinTeam(join), HttpStatus.OK);
	}
	
	// 그룹 탈퇴
	@PostMapping("/outTeam.run")
	public ResponseEntity<Boolean> outTeam(@RequestBody UserGroup userGroup) {
		log.debug("그룹 탈퇴 요청");
		return new ResponseEntity<Boolean>(groupService.outTeam(userGroup), HttpStatus.OK);
	}
	
	// 전체 그룹 정보 조회
//	@GetMapping("/findAllTeam.run")
//	public ResponseEntity<List<Group>> findAllTeam(HttpServletRequest request) {
//		log.debug("전체 그룹 정보 요청");
//		return new ResponseEntity<List<Group>>(groupService.findAllTeam(), HttpStatus.OK);
//	}
	@GetMapping("/findAllTeam.run")
	public ResponseEntity<List<Group>> findAllTeam(@RequestParam Integer uid) {
		log.debug("전체 그룹 정보 요청");
		return new ResponseEntity<List<Group>>(groupService.findAllTeam(uid), HttpStatus.OK);
	}
}
