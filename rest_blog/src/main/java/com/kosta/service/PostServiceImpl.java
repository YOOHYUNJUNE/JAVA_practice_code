package com.kosta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.damain.FileDTO;
import com.kosta.damain.PostRequest;
import com.kosta.damain.PostResponse;
import com.kosta.entity.ImageFile;
import com.kosta.entity.Post;
import com.kosta.entity.User;
import com.kosta.repository.ImageFileRepository;
import com.kosta.repository.PostRepository;
import com.kosta.repository.UserRepository;
import com.kosta.util.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ImageFileService imageFileService;
	
	
	// 게시글(이미지 포함) 추가 -> FileUtils
	@Override
	public PostResponse insertPost(PostRequest postDTO, MultipartFile file) {
		
		ImageFile savedImage = imageFileService.saveImage(file);
		if (savedImage != null) {			
			postDTO.setImageFile(savedImage);
		}
			
		// 게시글 추가
		User user = userRepository.findById(postDTO.getAuthorId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));
		Post post = postDTO.toEntity(user);
		Post savedPost = postRepository.save(post);
		PostResponse result = PostResponse.toDTO(savedPost);
		
		return result;
	}


	// 전체 게시물 보기
	@Override
	public List<PostResponse> getAllPost() {
		List<Post> postList = postRepository.findAll();
		
		if (postList.size() > 0 ) {
//			List<PostResponse> postResponseList = postList.stream().map(post -> PostResponse.toDTO(post)).toList();
			List<PostResponse> postResponseList = postList.stream().map(PostResponse::toDTO).toList();
			return postResponseList;							
		} else {
			return new ArrayList<>();
		}		
	}


	// 게시물 ID로 불러오기
	@Override
	public PostResponse getPostById(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없음"));
		PostResponse postResponse = PostResponse.toDTO(post);
		return postResponse;
	}


	// 게시물 수정
	@Override
	public PostResponse updatePost(PostRequest postDTO, MultipartFile file) {
		// 수정할 유저 확인
		User user = userRepository.findById(postDTO.getAuthorId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));
		// 원본 글 가져오기
		Post post = postRepository.findById(postDTO.getId())
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없음"));
		
		if(!post.getAuthor().getId().equals(user.getId())) {
			throw new IllegalArgumentException("본인의 글만 수정할 수 있습니다.");
		}
		if(!post.getPassword().equals(postDTO.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		ImageFile savedImage = imageFileService.saveImage(file);
		// 수정하지 않을 경우 그대로 놔두기(null로)
		if(savedImage != null) post.setImage(savedImage); 
		if(postDTO.getTitle() != null) post.setTitle(postDTO.getTitle());
		if(postDTO.getContent() != null) post.setContent(postDTO.getContent());
		
		Post updatedPost = postRepository.save(post);
		PostResponse result = PostResponse.toDTO(updatedPost);		
		return result;
	}


	// 게시글 삭제
	@Override
	public PostResponse deletePost(Long id, PostRequest postDTO) {
		// 삭제 유저 확인
		User user = userRepository.findById(postDTO.getAuthorId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));
		// 원본 글 가져오기
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없음"));
		
		if(!post.getAuthor().getId().equals(user.getId())) {
			throw new IllegalArgumentException("본인의 글만 삭제할 수 있습니다.");
		}
		if(!post.getPassword().equals(postDTO.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		// 게시물 삭제시 첨부파일도 삭제 (cascade 설정하기)
//		postRepository.deleteById(post.getImage().getId());
		
		postRepository.delete(post);
		return PostResponse.toDTO(post);
		
	}



	
	
	
	
	
	
	
	
	
	
	
}
