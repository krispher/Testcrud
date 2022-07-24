package com.example.service;

import com.example.model.UserEntity;
import com.example.repository.UserResult;

import lombok.NonNull;

public interface IUserService {
	
	UserResult add(UserEntity userEntity);
	
	UserResult updateUser(Integer userId, UserEntity user);
	
	UserResult deleteUser(Integer userId);
	
	@NonNull
	default UserResult addUserSuccessful(final @NonNull UserEntity saved) {
		return UserResult.builder()
				.success(true)
				.user(saved)
				.build();
	}
	
	@NonNull
	default UserResult duplicateUSer(final @NonNull UserEntity duplicate) {
		return UserResult.builder()
				.success(false)
				.user(duplicate)
				.errorMessage("The taxIdNumber: " + duplicate.getTaxIdNumber()+ " already exist.")
				.build();
	}
	
	@NonNull
    default UserResult invalidTaxIdNumber(final @NonNull UserEntity invalidate) {
        return UserResult.builder()
                .success(false)
                .user(invalidate)
                .errorMessage("The taxIdNumber: " + invalidate.getTaxIdNumber() +" is invalidate format.")
                .build();
    }
	
	@NonNull
    default UserResult updatedUserSuccessful(final UserEntity updated) {
        return UserResult.builder()
                .success(true)
                .user(updated)
                .build();
    }
	
	@NonNull
    default UserResult deletedUserSuccessful() {
        return UserResult.builder()
                .success(true)
                .user(null)
                .build();
    }
	
	@NonNull
    default UserResult notFoundUser(final Integer notFound) {
        return UserResult.builder()
                .success(false)
                .user(null)
                .errorMessage("The id dont exist ")
                .build();
    }

}
