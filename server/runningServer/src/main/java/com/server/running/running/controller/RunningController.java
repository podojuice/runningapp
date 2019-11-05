package com.server.running.running.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.server.running.running.dto.Running;
import com.server.running.running.service.RunningService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RunningController {
	@Autowired
	private RunningService runningService;
	
	// 러닝 시작
	@PostMapping("/start.run")
	public ResponseEntity<Running> start(@RequestBody Running running) {
		log.debug("러닝 시작 요청");
		return new ResponseEntity<Running>(runningService.start(running), HttpStatus.OK);
	}
	
	// 러닝 종료
	@PutMapping("/end.run")
	public ResponseEntity<Running> end(@RequestBody Running running) {
		log.debug("러닝 종료 요청");
		return new ResponseEntity<Running>(runningService.end(running), HttpStatus.OK);
	}
}
