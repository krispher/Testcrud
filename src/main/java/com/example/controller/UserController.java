package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.ModelNotFoundException;
import com.example.model.UserEntity;
import com.example.repository.IUserRepository;
import com.example.repository.UserResult;
import com.example.service.IUserService;

import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping(UserController.URI)
@AllArgsConstructor
public class UserController {

	protected static final String URI = "api/v1/user";

	@Autowired
	private IUserRepository iUserRepo;

	@Autowired
	private IUserService userServiceRepo;

	@GetMapping()
	public ResponseEntity<List<UserEntity>> getAllUser() throws Exception {
		final List<UserEntity> result = (List<UserEntity>) iUserRepo.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> newUser(@Validated @RequestBody UserEntity user) throws Exception {

		final UserResult res = userServiceRepo.add(user);

		if (res.isSuccess()) {
			UserEntity saved = res.getUser();
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		}

		throw new ModelNotFoundException(res.getErrorMessage());
	}

	@PutMapping("{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody final UserEntity user)
			throws Exception {

		final UserResult result = userServiceRepo.updateUser(userId, user);

		if (result.isSuccess()) {
			UserEntity saved = result.getUser();
			return new ResponseEntity<>(saved, HttpStatus.OK);
		}

		throw new ModelNotFoundException(result.getErrorMessage());
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") Integer id) throws Exception {

		final UserResult res = userServiceRepo.deleteUser(id);

		if (res.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} 
			throw new ModelNotFoundException(res.getErrorMessage() + id);
		
	}
}
