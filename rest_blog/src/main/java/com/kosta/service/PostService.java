package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.damain.FileDTO;
import com.kosta.damain.PostRequest;
import com.kosta.damain.PostResponse;

public interface PostService {

	PostResponse insertPost(PostRequest post, MultipartFile file);

	List<PostResponse> getAllPost();

	PostResponse getPostById(Long id);

	PostResponse updatePost(PostRequest post, MultipartFile file);

	PostResponse deletePost(Long id, PostRequest postRequest);

}
