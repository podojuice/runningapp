package com.server.running.running.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.running.dto.Running;
import com.server.running.running.repository.RunningRepository;
import com.server.running.user.dto.User;

@Service
public class RunningServiceImpl implements RunningService {
	@Autowired
	private RunningRepository runningRepository;
	
	// 러닝 시작
	@Override
	public Running start(Running running) {
		running.setDistance(0.0);
		runningRepository.save(running);
		return running;
	}

	// 러닝 종료
	@Override
	public Running end(Running running) {
		runningRepository.save(running);
		return runningRepository.findById(running.getRid()).get();
	}
}
