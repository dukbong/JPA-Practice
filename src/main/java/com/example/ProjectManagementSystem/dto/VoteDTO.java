package com.example.ProjectManagementSystem.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteDTO {
	private String creator;
	private String title;
	private String content;
	private final LocalDate startDate = LocalDate.now();
	private LocalDate endDate;
}
