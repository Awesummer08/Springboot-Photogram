package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesService {

	public final LikesRepository likesRepository;
	
	@Transactional
	public void 좋아요(int imageId, int principalDetails) {
		likesRepository.mLikes(imageId, principalDetails);
	}
	
	@Transactional
	public void 좋아요취소(int imageId, int principalDetails) {
		likesRepository.mUnLikes(imageId, principalDetails);
	}
}
