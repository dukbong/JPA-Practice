package com.example.ProjectManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjectManagementSystem.entity.Team;

public interface TeamRepo extends JpaRepository<Team, Long>{

}
