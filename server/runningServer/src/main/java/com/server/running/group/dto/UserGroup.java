package com.server.running.group.dto;

import com.server.running.user.dto.User;

import lombok.Data;

@Data
public class UserGroup {
	private Group group;
	private User user;
}
