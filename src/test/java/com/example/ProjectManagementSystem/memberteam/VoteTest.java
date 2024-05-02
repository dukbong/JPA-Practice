package com.example.ProjectManagementSystem.memberteam;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ProjectManagementSystem.entity.Member;
import com.example.ProjectManagementSystem.entity.Team;
import com.example.ProjectManagementSystem.entity.Vote;
import com.example.ProjectManagementSystem.entity.VoteResponse;
import com.example.ProjectManagementSystem.enums.Power;
import com.example.ProjectManagementSystem.repository.MemberRepo;
import com.example.ProjectManagementSystem.repository.TeamRepo;
import com.example.ProjectManagementSystem.repository.VoteRepo;
import com.example.ProjectManagementSystem.repository.VoteResponseRepo;

import jakarta.transaction.Transactional;

@SpringBootTest
public class VoteTest {
	@Autowired
	MemberRepo memberRepo;
	@Autowired
	TeamRepo teamRepo;
	@Autowired
	VoteRepo voteRepo;
	@Autowired
	VoteResponseRepo voteResponseRepo;
	
	@Test
	@Transactional
	void voteTest() {
		Member member = Member.builder().name("tester").phoneNumber("1111").email("111@111.com").build();
		Member member2 = Member.builder().name("member1").phoneNumber("222").email("222@111.com").build();
		memberRepo.save(member);
		memberRepo.save(member2);
		
		Team team = member.createTeam("ATeam");
		teamRepo.save(team);
		team.addMember(member);
		team.addMember(member2);
		
		assertThat(member.getTeam().getTeamName()).isEqualTo("ATeam");
		assertThat(member2.getTeam().getTeamName()).isEqualTo("ATeam");
		assertThat(member.getPower()).isEqualTo(Power.LEADER);
		assertThat(member2.getPower()).isEqualTo(Power.USER);
		assertThat(team.getMembers().size()).isEqualTo(2);
		
		Vote vote = member2.createVote("test vote title", "test vote content", LocalDate.now().plusDays(1));
		voteRepo.save(vote);
		
		VoteResponse voteResponse = member2.responseToVote(vote, true);
		voteResponseRepo.save(voteResponse);
		
		assertThat(voteResponseRepo.findAll().size()).isEqualTo(1);
	}
}
