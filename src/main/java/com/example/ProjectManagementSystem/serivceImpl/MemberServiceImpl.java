package com.example.ProjectManagementSystem.serivceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.ProjectManagementSystem.dto.MemberCreateTeamDTO;
import com.example.ProjectManagementSystem.dto.MemberDTO;
import com.example.ProjectManagementSystem.entity.Member;
import com.example.ProjectManagementSystem.entity.Team;
import com.example.ProjectManagementSystem.repository.MemberRepo;
import com.example.ProjectManagementSystem.repository.TeamRepo;
import com.example.ProjectManagementSystem.serivce.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepo memberRepo;
	private final TeamRepo teamRepo;
	
	@Override
	public void createMember(MemberDTO memberDTO) {
		memberRepo.save(Member.builder()
							  .name(memberDTO.getName())
							  .phoneNumber(memberDTO.getPhoneNumber())
							  .email(memberDTO.getEmail())
							  .build());
	}

	@Override
	@Transactional
	public void createTeam(MemberCreateTeamDTO memberCreateTeamDTO) {
		Optional<Member> findMember = memberRepo.findByName(memberCreateTeamDTO.getName());
		Assert.isTrue(findMember.isPresent(), "현재 memberDTO는 없는 멤버 입니다.");
		Team team = findMember.get().createTeam(memberCreateTeamDTO.getTeamName());
		teamRepo.save(team);
		team.addMember(findMember.get());
	}

}
