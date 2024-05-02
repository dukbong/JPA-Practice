package com.example.ProjectManagementSystem.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import com.example.ProjectManagementSystem.dto.MemberDTO;
import com.example.ProjectManagementSystem.enums.Power;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/***
 * Member : Team  = N : 1
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "member_gen", sequenceName = "member_seq", initialValue = 1, allocationSize = 50)
public class Member {

	@Id
	@GeneratedValue(generator = "member_gen", strategy = GenerationType.SEQUENCE)
	@Column(name = "member_id")
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Power power;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<VoteResponse> voteResponses = new ArrayList<>();
	
	@Builder
	public Member(Long id, String name, String phoneNumber, String email) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.power = Power.USER;
	}
	
	public MemberDTO convertDTO() {
		return MemberDTO.builder().name(this.name).phoneNumber(this.phoneNumber).email(this.email).build();
	}
	
	public void joinTeam(Team newTeam) {
		Assert.isTrue(this.team == null, "이미 팀에 속해 있습니다.");
		this.team = newTeam;
	}
	
	public void removeTeam() {
		this.team = null;
	}
	
	public Team createTeam(String teamName) {
		Assert.isTrue(this.team == null, "이미 팀에 속해 있습니다.");
		this.power = Power.LEADER;
		return Team.builder().teamName(teamName).leader(this.name).build();
	}
	
	public Vote createVote(String title, String content, LocalDate endDate) {
		Assert.hasText(title, "제목은 필수 사항입니다.");
		Assert.hasText(content, "내용은 필수 사항입니다.");
		Assert.notNull(endDate, "투표 만료 시간은 필수 사항입니다.");
		
		return Vote.builder().creator(this.name).title(title).content(content).endDate(endDate).build();
	}
	
	public VoteResponse responseToVote(Vote vote, boolean isUpVote) {
		Assert.notNull(vote, "해당 투표는 존재하지 않습니다.");
		
		boolean checkVote = voteResponses.stream().anyMatch(vr -> vr.getVote().getId().equals(vote.getId()));
		
		Assert.isTrue(!checkVote, "이미 이 투표에 응답했습니다.");
		
		VoteResponse voteResponse = VoteResponse.builder().vote(vote).member(this).isUpVote(isUpVote).build();
		
		return voteResponse;
	}
	
}
