package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserEntity;
import com.example.repository.IUserRepository;
import com.example.repository.UserResult;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepo;

	public UserResult add(UserEntity userEntity) {
		return userRepo.findUserByTaxIdNumber(userEntity.getTaxIdNumber()).map(this::handleDuplicateTaxId)
				.orElseGet(() -> internalAddUser(userEntity));
	}

	private UserResult handleDuplicateTaxId(UserEntity existingUser) {
		return duplicateUSer(existingUser);
	}

	private UserResult internalAddUser(UserEntity user) {

		boolean isValidTaxIdNumber = isValidTaxIdNumber(user.getTaxIdNumber());

		if (isValidTaxIdNumber) {
			UserEntity saved = userRepo.save(user);
			return addUserSuccessful(saved);
		}
		return invalidTaxIdNumber(user);
	}

	private boolean isValidTaxIdNumber(String rfc) {
		rfc.toUpperCase().trim();
		return rfc.toUpperCase().matches("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}");
	}

	private UserResult internalUpdateUser(UserEntity user) {

		boolean isValidTaxIdNumber = isValidTaxIdNumber(user.getTaxIdNumber());

		if (isValidTaxIdNumber) {
			userRepo.save(user);

			return updatedUserSuccessful(user);
		}
		return invalidTaxIdNumber(user);
	}

	public UserResult updateUser(Integer userId, UserEntity dto) {
		dto.setUserId(userId);

		Optional<UserEntity> user = userRepo.findById(userId);
		if (user.isPresent()) {
			return internalUpdateUser(dto);
		} else {
			return notFoundUserById(userId);
		}
	}

	private UserResult notFoundUserById(Integer notFound) {
		return notFoundUser(notFound);
	}

	public UserResult deleteUser(Integer userId) {
		Optional<UserEntity> user = userRepo.findById(userId);
		if (user.isPresent()) {
			return internalDeleteUser(userId);
		} else {
			return notFoundUser(userId);
		}
	}

	private UserResult internalDeleteUser(Integer userId) {

		userRepo.deleteById(userId);
		return deletedUserSuccessful();
	}

}
