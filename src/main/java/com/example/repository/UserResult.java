package com.example.repository;

import java.util.Optional;

import com.example.model.UserEntity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResult {
	
	@Builder.Default
	private final boolean success = false;
	private final UserEntity user;
	private final String errorMessage;
	private final Optional<Throwable> errorCause;

}
