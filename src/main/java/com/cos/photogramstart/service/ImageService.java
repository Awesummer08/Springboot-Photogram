package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(){
		return imageRepository.mPopular(); 
	}
	
	@Transactional(readOnly = true) 
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStroy(principalId, pageable);
		
		//2번으로 로그인했다. 이미지를 쫙 들고 왔다(2번이 구독하는중인) 
		// 이미지를 for문으로 뽑느다.
		// 첫 번째 이미지 뽑았다. 첫번째 이미지를 좋아요하는 정보를 for문으로 뽑는다. => 이 좋아요가 내가 좋아요 한건지 찾아내면 된다.
		
		//	if(like.getUser().getId() == principalId)  이게 같다면 내가 좋아요를 한 것! 
		
		// images에 좋아요 상태 담기
		images.forEach((image) -> {
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like) -> {  
				if(like.getUser().getId() == principalId) { 
					image.setLikeState(true);
				} 
			});
			
		});
		
		return images;
	} 
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid +"_"+imageUploadDto.getFile().getOriginalFilename();
		System.out.println("이미지 파일이름"+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName); 
		
		try { 
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
	
		//System.out.println(imageEntity);
		
	}
}
