package com.example.ProjectManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjectManagementSystem.entity.Member;

public interface MemberRepo extends JpaRepository<Member, Long> {
	Optional<Member> findByName(String name);
}
