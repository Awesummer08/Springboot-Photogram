package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto { 
	private int id;
	private String username;
	private String profileImageUrl;
	private Integer subscribeState; // mariadb에서는 Integer로 해야 반환 받을 수 있다.
	private Integer equalUserState;
} 

