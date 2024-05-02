package com.example.ProjectManagementSystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateTeamDTO {

	private String name;
	private String phoneNumber;
	private String email;
	private String teamName;
	
	@Builder
	public MemberCreateTeamDTO(String name, String phoneNumber, String email, String teamName) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.teamName = teamName;
	}
}