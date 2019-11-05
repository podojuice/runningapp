package com.server.running.plan.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "groupPlan")
public class GroupPlan {
	// 기본키
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 플랜명
	@Column
	private String name;
	
	// 목표 거리
	@Column
	private Double distanceTarget;
	
	// 그룹 번호
	@Column
	private Integer groupId;

	// 시작 날짜
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	// 종료 날짜
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
}
