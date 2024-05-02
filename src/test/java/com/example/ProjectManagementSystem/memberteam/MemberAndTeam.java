package com.example.ProjectManagementSystem.memberteam;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ProjectManagementSystem.entity.Member;
import com.example.ProjectManagementSystem.entity.Team;
import com.example.ProjectManagementSystem.repository.MemberRepo;
import com.example.ProjectManagementSystem.repository.TeamRepo;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemberAndTeam {

	@Autowired
	TeamRepo teamRepo;
	
	@Autowired
	MemberRepo memberRepo;
	
	@Test
	@Transactional
	void memberAndTeam() {
		Member leaderMember = Member.builder().name("leaderMember").phoneNumber("111").email("111@111.com").build();
		Member member1 = Member.builder().name("member1").phoneNumber("222").email("222@222.com").build();
		memberRepo.save(leaderMember);
		memberRepo.save(member1);
		
		Team team = leaderMember.createTeam("testTeam");
		teamRepo.save(team);
		team.addMember(leaderMember);
		team.addMember(member1);
		
		
		List<Member> members = memberRepo.findAll();
		for(Member mem : members) {
			assertThat(mem.getTeam().getTeamName()).isEqualTo("testTeam");
		}
		
	}
}
