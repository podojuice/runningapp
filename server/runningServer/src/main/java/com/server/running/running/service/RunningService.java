package com.server.running.running.service;

import java.util.List;

import com.server.running.running.dto.Running;
import com.server.running.user.dto.User;

public interface RunningService {
	// 러닝 시작
	public Running start(Running running);
	
	// 러닝 종료
	public Running end(Running running);

}
