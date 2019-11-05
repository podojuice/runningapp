package com.server.running.running.dto;

import lombok.Data;

@Data
public class FriendRunning {
	private Running running;
	private String userEmail;
	private String userName;
}
