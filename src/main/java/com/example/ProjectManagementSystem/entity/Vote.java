package com.example.ProjectManagementSystem.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "vote_gen", sequenceName = "vote_seq", initialValue = 1, allocationSize = 50)
public class Vote {

	@Id
	@GeneratedValue(generator = "vote_gen")
	private Long id;
	
	private String creator;
	
	private String title;
	
	private String content;
	
	private LocalDate startDate = LocalDate.now();
	
	private LocalDate endDate;
	
	@OneToMany(mappedBy = "vote", fetch = FetchType.LAZY)
	private List<VoteResponse> voteResponses = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@Builder
	public Vote(Long id, String creator, String title, String content, LocalDate endDate) {
		this.id = id;
		this.creator = creator;
		this.title = title;
		this.content = content;
		this.endDate = endDate;
	}
	
	public List<Member> checkVote(){
		List<Member> checkMember = new ArrayList<>();
		for(VoteResponse voteResponse : voteResponses) {
			checkMember.add(voteResponse.getMember());
		}
		return checkMember;
	}
	
	public int isUpVote() {
		return this.voteResponses.stream().filter(vr -> vr.getUpVote() == true).collect(Collectors.toList()).size();
	}
	
	public int isDownVote() {
		return this.voteResponses.stream().filter(vr -> vr.getUpVote() == false).collect(Collectors.toList()).size();
	}
}
