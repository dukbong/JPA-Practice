package com.example.ProjectManagementSystem.serivce;

import com.example.ProjectManagementSystem.dto.MemberCreateTeamDTO;
import com.example.ProjectManagementSystem.dto.MemberDTO;

public interface MemberService {

	void createMember(MemberDTO memberDTO);

	void createTeam(MemberCreateTeamDTO memberCreateTeamDTO);
}
