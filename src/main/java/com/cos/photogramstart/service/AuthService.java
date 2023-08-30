package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Transactional
	public User 회원가입(User user) {
		User entityUser = userRepository.save(user);
		String rawPassword = user.getPassword();
		String encodePassword = bcryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encodePassword);
		user.setRole("ROLE_USER");
		
		return entityUser;
	}
	
}
