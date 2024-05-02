package com.example.ProjectManagementSystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDTO {

	private String name;
	private String phoneNumber;
	private String email;
	
	@Builder
	public MemberDTO(String name, String phoneNumber, String email) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
}
