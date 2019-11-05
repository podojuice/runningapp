package com.server.running.group.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.server.running.plan.dto.GroupPlan;
import com.server.running.user.dto.User;

import lombok.Data;

@Data
@Entity
@Table(name="team")
public class Group {
	// 기본키
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gid;
	
	// 그룹명
	@Column
	private String name;
	
	// 챌린지 내용
	@Column
	private String content;
	
	// 목표 거리
	@Column
	private double distance;
	
	// 운동 기간
	@Column
	private String period;
	
	// 참여자 수
	private Integer runnerSum;
	
	// 해당 유저 참여 여부
	private boolean active;
	
	// 그룹의 플랜 리스트
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name="groupId")
//	private List<GroupPlan> groupPlans;
	
	// 그룹의 유저 리스트
	@JsonIgnoreProperties("groups")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "userGroup", 
				joinColumns = @JoinColumn(name = "gid"),
				inverseJoinColumns = @JoinColumn(name = "uid"))
	private List<User> users;
	
	// 유저 추가(relationship)
	public boolean addUsers(User user) {
		if(users == null) {
			users = new ArrayList<>();
		}
		return users.add(user);
	}
	
	// 유저 제거(relationship)
	public int deleteUsers(User user) {
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getUid() == user.getUid()) {
				users.remove(i);
				break;
			}
		}
		return users.size();
	}
}
