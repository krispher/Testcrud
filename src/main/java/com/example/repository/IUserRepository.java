package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.UserEntity;

import lombok.NonNull;

public interface IUserRepository extends JpaRepository<UserEntity, Integer>{
	
	@NonNull
	Optional<UserEntity> findUserByTaxIdNumber(String taxIdNumber);
}