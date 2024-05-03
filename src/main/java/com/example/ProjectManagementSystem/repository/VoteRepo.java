package com.example.ProjectManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjectManagementSystem.entity.Vote;

public interface VoteRepo extends JpaRepository<Vote, Long> {
	List<Vote> findByTeamId(Long teamId);
}
