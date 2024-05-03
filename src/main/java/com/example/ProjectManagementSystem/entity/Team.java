package com.example.ProjectManagementSystem.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.example.ProjectManagementSystem.dto.TeamDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "team_gen", sequenceName = "team_seq", initialValue = 1, allocationSize = 50)
public class Team {

	@Id
	@GeneratedValue(generator = "team_gen")
	@Column(name = "team_id")
	private Long id;

	private String teamName;

	private String leader;

	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private List<Member> members = new ArrayList<>();
	
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private List<Vote> votes = new ArrayList<>();

	@Builder
	public Team(Long id, String teamName, String leader) {
		this.id = id;
		this.teamName = teamName;
		this.leader = leader;
	}

	public TeamDTO converDTO() {
		return TeamDTO.builder().teamName(this.teamName).leader(this.leader).build();
	}

	private boolean containCheck(Member member) {
		return this.members.stream().anyMatch(m -> m.getId().equals(member.getId()));
	}
	private boolean containCheck(Vote vote) {
		return this.votes.stream().anyMatch(v -> v.getId().equals(vote.getId()));
	}

	public void addMember(Member member) {
		Assert.isTrue(!containCheck(member), "해당 멤버는 이미 팀의 구성원 입니다.");
		this.members.add(member);
		member.joinTeam(this);
	}
	
	public void addVote(Vote vote) {
		Assert.isTrue(!containCheck(vote), "해당 멤버는 이미 팀의 구성원 입니다.");
		this.votes.add(vote);
	}

	public void removeMember(Member member) {
		Assert.isTrue(containCheck(member), "해당 멤버는 팀의 구성원이 아닙니다.");
		this.members.removeIf(m -> m.getId().equals(member.getId()));
		member.removeTeam();
	}
	
}
