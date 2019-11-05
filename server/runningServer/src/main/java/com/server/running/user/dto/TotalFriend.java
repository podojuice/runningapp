package com.server.running.user.dto;

import lombok.Data;

@Data
public class TotalFriend implements Comparable<TotalFriend>{
	private String userEmail;
	private String userName;
	private Double wholeDistance;
	
	@Override
	public int compareTo(TotalFriend o) {
		return (int)(o.wholeDistance - this.wholeDistance);
	}
}
