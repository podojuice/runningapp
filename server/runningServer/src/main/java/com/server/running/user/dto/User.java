package com.server.running.user.dto;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysql.cj.jdbc.Blob;
import com.server.running.group.dto.Group;
import com.server.running.plan.dto.UserPlan;
import com.server.running.running.dto.Running;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	// 기본키
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;
	
	// 아이디
	@Column(unique = true)
	private String email;
	
	// 비밀번호
	@Column
	private String password;
	
	// 이름
	@Column
	private String name;
	
	// 키
	@Column
	private String height;
	
	// 체중
	@Column
	private String weight;
	
	// 성별
	@Column
	private String gender;
	
	// 프로필 사진
	@Column
	private String img;
	
	// 유저의 플랜 리스트
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="userId")
	private List<UserPlan> userPlans;
	
	// 유저의 그룹 리스트
	@JsonIgnoreProperties("users")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "users")
	private List<Group> groups;
	
	// 유저의 친구 리스트
	@JsonIgnoreProperties("friends")
	@JoinTable(name = "friends", joinColumns = {
			@JoinColumn(name = "lid", referencedColumnName = "uid")
	}, inverseJoinColumns = {
			@JoinColumn(name = "rid", referencedColumnName = "uid")
	})
	@ManyToMany
	private List<User> friends;
	
	// 유저의 러닝 데이터 리스트
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private List<Running> runningData;
	
	// 그룹 추가(relationship)
	public boolean addGroups(Group group) {
		if(groups == null) {
			groups = new ArrayList<>();
		}
		return groups.add(group);
	}
	
	// 친구 추가(relationship)
	public boolean addFriends(User user) {
		if(friends == null) {
			friends = new ArrayList<>();
		}
		return friends.add(user);
	}
}
