package com.example.ProjectManagementSystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProjectManagementSystem.dto.MemberCreateTeamDTO;
import com.example.ProjectManagementSystem.dto.MemberDTO;
import com.example.ProjectManagementSystem.serivce.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	@PostMapping("/create/member")
	public void createMember(@RequestBody MemberDTO memberDTO) {
		memberService.createMember(memberDTO);
	}
	
	@PostMapping("/create/team")
	public void createTeam(@RequestBody MemberCreateTeamDTO memberCreateTeamDTO) {
		memberService.createTeam(memberCreateTeamDTO);
	}
	
}
