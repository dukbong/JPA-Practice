package com.example.ProjectManagementSystem.total;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
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
	@Transactional
	void totalTest() {
		// 회원 저장
		Member member1 = Member.builder().name("member1").email("member1@gmail.com").phoneNumber("1111@1111").build();
		Member member2 = Member.builder().name("member2").email("member1@gmail.com").phoneNumber("1111@1111").build();
		Member member3 = Member.builder().name("member4").email("member1@gmail.com").phoneNumber("1111@1111").build();
		Member member4 = Member.builder().name("member5").email("member1@gmail.com").phoneNumber("1111@1111").build();
		memberRepo.save(member1);
		memberRepo.save(member2);
		memberRepo.save(member3);
		memberRepo.save(member4);
		
		// 팀 생성
		Team team1 = member1.createTeam("ATEAM");
		teamRepo.save(team1);
		team1.addMember(member1);
		team1.addMember(member2);
		
		Team team2 = member3.createTeam("BTEAM");
		teamRepo.save(team2);
		team2.addMember(member3);
		team2.addMember(member4);
		
		// 투표 만들기
		Vote vote1 = member1.createVote("title", "content", LocalDate.now().plusDays(1));
		voteRepo.save(vote1);
		team1.addVote(vote1);
		Vote vote11 = member1.createVote("title11", "content11", LocalDate.now().plusDays(1));
		voteRepo.save(vote11);
		team1.addVote(vote11);
		
		Vote vote2 = member3.createVote("title2", "content2", LocalDate.now().plusDays(1));
		voteRepo.save(vote2);
		team2.addVote(vote2);
		
		VoteResponse member1Vote = member1.responseToVote(vote1, false);
		voteResponseRepo.save(member1Vote);
		member1Vote.addToMemberAndTeam();

		// 멤버가 속한 팀의 투표 내용 찾기
		Optional<Member> findMember = memberRepo.findByName(member1.getName());
		if(findMember.isPresent()) {
			Team findTeam = findMember.get().getTeam();
			Optional<Vote> findVote = findTeam.getVotes().stream().filter(fv -> fv.getCreator().equals(findMember.get().getName()) && fv.getTitle().equals("title")).findFirst();
			// 해당 투표에 관한 내용들 확인하기
			log.info("해당 투표는 {} 팀에서 진행하고 있습니다.", findTeam.getTeamName());
			log.info("투표 제목 : {}", findVote.get().getTitle());
			log.info("투표 내용 : {}", findVote.get().getContent());
			log.info("투표 할 수 있는 총 인원 : {}", findTeam.getMembers().size());
			log.info("투표를 진행한 인원 : {}, 미 투표 인원 : {}", findVote.get().checkVote().size(), findTeam.getMembers().size() - findVote.get().checkVote().size());
			log.info("찬성 : {} vs 반대 : {}", findVote.get().isUpVote(), findVote.get().isDownVote());
		}
		
	}
}
