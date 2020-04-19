package com.wallet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.entity.User;
import com.wallet.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User usuario) {
		return userRepository.save(usuario);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmailEquals(email);
	}
}