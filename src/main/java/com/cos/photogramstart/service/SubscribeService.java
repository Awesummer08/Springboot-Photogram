package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeReposiotry;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {

	private final SubscribeReposiotry subscribeReposiotry;
	private final EntityManager em;
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		
		//쿼리 준비
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if ((SELECT TRUE from subscribe where fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if ((?=u.id), 1, 0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s "); 
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ? "); //마지막꺼 세미콜론 첨부하면 안되고 모든 문장은 마지막에 한 칸씩 띄워야한다.
		
		// 1.물음표  fromUserId = ? 로그인한 아이디 principalId
		// 2.물음표 1=u.id = 로그인한 아이디 principalId
		// 3.물음표  마지막 물음표 = 페이지 주인 pageUserId

		//여기의 쿼리는 java.persistence 쿼리
		//쿼리 완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId); 
		
		//쿼리 실행
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class); //쿼리, 리턴타입 넣기
		
		return subscribeDtos; 
	}      
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeReposiotry.mSubscribe(fromUserId, toUserId);			
		} catch (Exception e) {
			throw new CustomApiException("이미 구독했습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeReposiotry.mUnSubscribe(fromUserId, toUserId);
	}
}
