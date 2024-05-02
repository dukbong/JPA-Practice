package com.example.ProjectManagementSystem.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "vote_res_gen", sequenceName = "vote_res_seq", initialValue = 1, allocationSize = 50)
public class VoteResponse {
	@Id
	@GeneratedValue(generator = "vote_res_gen")
	@Column(name = "vote_res_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vote_id", nullable = false)
	private Vote vote;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	private boolean isUpVote;

	@Builder
	public VoteResponse(Vote vote, Member member, boolean isUpVote) {
		this.vote = vote;
		this.member = member;
		this.isUpVote = isUpVote;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteResponse that = (VoteResponse) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
	
    public void addToMember() {
        if (!member.getVoteResponses().contains(this)) {
            member.getVoteResponses().add(this);
        }
    }
    
    public void addToVote() {
        if (!vote.getVoteResponses().contains(this)) {
            vote.getVoteResponses().add(this);
        }
    }
}
