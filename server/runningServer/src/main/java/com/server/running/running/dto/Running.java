package com.server.running.running.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "runningdata")
public class Running {
	// 기본키
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rid;
	
	// 시작 시간
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private LocalDateTime starttime;
	
	// 종료 시간
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	private LocalDateTime endtime;
	
	// 이동 거리
	@Column
	private Double distance;
	
	// 유저 번호
	@Column
	private Integer userId;
	
	// 지도
	@Lob
	@Column
	private String image;
}
