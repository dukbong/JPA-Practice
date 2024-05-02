package com.example.ProjectManagementSystem.total;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ProjectManagementSystem.entity.Member;
import com.example.ProjectManagementSystem.entity.Team;
import com.example.ProjectManagementSystem.entity.Vote;
import com.example.ProjectManagementSystem.entity.VoteResponse;
import com.example.ProjectManagementSystem.repository.MemberRepo;
import com.example.ProjectManagementSystem.repository.TeamRepo;
import com.example.ProjectManagementSystem.repository.VoteRepo;
import com.example.ProjectManagementSystem.repository.VoteResponseRepo;

@SpringBootTest
public class TotalTest {

	@Autowired
	MemberRepo memberRepo;
	@Autowired
	TeamRepo teamRepo;
	@Autowired
	VoteRepo voteRepo;
	@Autowired
	VoteResponseRepo voteResponseRepo;
	
	@Test
	void totalTest() {
		// 회원 저장
		Member member1 = Member.builder().name("member1").email("member1@gmail.com").phoneNumber("1111@1111").build();
		Member member2 = Member.builder().name("member1").email("member1@gmail.com").phoneNumber("1111@1111").build();
		Member member3 = Member.builder().name("member1").email("member1@gmail.com").phoneNumber("1111@1111").build();
		memberRepo.save(member1);
		memberRepo.save(member2);
		memberRepo.save(member3);
		
		// 팀 생성
		Team team = member1.createTeam("ATEAM");
		teamRepo.save(team);
		team.addMember(member1);
		team.addMember(member2);
		team.addMember(member3);
		
		// 투표 만들기
		Vote vote = member3.createVote("title", "content", LocalDate.now().plusDays(1));
		voteRepo.save(vote);
		
		// 투표 하기
		VoteResponse voteResponse = member1.responseToVote(vote, false);
		voteResponseRepo.save(voteResponse);
		voteResponse.addToMember();
		voteResponse.addToVote();
		
		VoteResponse voteResponse2 = member2.responseToVote(vote, true);
		voteResponseRepo.save(voteResponse2);
		voteResponse2.addToMember();
		voteResponse2.addToVote();
	}
}
