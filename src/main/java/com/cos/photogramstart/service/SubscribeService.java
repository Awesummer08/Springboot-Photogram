package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeReposiotry;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {

	private final SubscribeReposiotry subscribeReposiotry;
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		subscribeReposiotry.mSubscribe(fromUserId, toUserId);
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeReposiotry.mUnSubscribe(fromUserId, toUserId);
	}
}
