package com.server.running.group.service;

import java.util.List;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.Join;
import com.server.running.group.dto.UserGroup;

public interface GroupService {
	// 그룹 생성
	public Boolean createTeam(UserGroup userGroup);
	
	// 그룹 수정
	public Boolean updateTeam(Group group);
	
	// 그룹 삭제
	public Boolean deleteTeam(Group group);
	
	// 그룹에 참가
	public Boolean joinTeam(Join join);
	
	// 그룹 탈퇴
	public Boolean outTeam(UserGroup userGroup);
	
	// 전체 그룹 정보 조회
	public List<Group> findAllTeam(int uid);
}
