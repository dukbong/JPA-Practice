package com.example.ProjectManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjectManagementSystem.entity.Vote;

public interface VoteRepo extends JpaRepository<Vote, Long> {

}
