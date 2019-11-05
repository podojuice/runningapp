package com.server.running.group.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.Join;
import com.server.running.group.dto.UserGroup;
import com.server.running.group.repository.GroupRepository;
import com.server.running.user.dto.User;
import com.server.running.user.repository.UserRepository;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private UserRepository userRepository;
	
	// 그룹 생성
	@Override
	public Boolean createTeam(UserGroup userGroup) {
		userGroup.getGroup().addUsers(userGroup.getUser());
		groupRepository.save(userGroup.getGroup());
		return true;
	}
	
	// 그룹 수정
	@Override
	public Boolean updateTeam(Group group) {
		groupRepository.save(group);
		return true;
	}
	
	// 그룹 삭제
	@Override
	public Boolean deleteTeam(Group group) {
		groupRepository.delete(group);
		return true;
	}
	
	// 그룹에 참가
	@Override
	public Boolean joinTeam(Join join) {
		Optional<Group> maybeGroup = groupRepository.findById(join.getGid());
		Optional<User> maybeUser = userRepository.findById(join.getUid());
		maybeGroup.get().addUsers(maybeUser.get());
		groupRepository.save(maybeGroup.get());
		return true;
	}
	
	// 그룹 탈퇴
	@Override
	public Boolean outTeam(UserGroup userGroup) {
		Optional<Group> maybeGroup = groupRepository.findById(userGroup.getGroup().getGid());
		int size = maybeGroup.get().deleteUsers(userGroup.getUser());
		if(size > 0) {
			groupRepository.save(maybeGroup.get());
		} else {
			groupRepository.delete(maybeGroup.get());
		}
		return true;
	}

	@Override
	public List<Group> findAllTeam(int uid) {
		List<Group> groups = groupRepository.findAll();
		Optional<User> user = userRepository.findById(uid);
		for (Group group : groups) {
			group.setRunnerSum(group.getUsers().size());
			group.setActive(false);
			if(user.isPresent()) {
				for (Group temp : user.get().getGroups()) {
					if (temp.getGid() == group.getGid()) {
						group.setActive(true);
						break;
					}
				}
			}
		}
		return groups;
	}
}
