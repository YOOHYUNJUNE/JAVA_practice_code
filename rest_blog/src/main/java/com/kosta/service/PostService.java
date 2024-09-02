package com.kosta.service;

import java.util.List;

import com.kosta.damain.PostRequest;
import com.kosta.damain.PostResponse;

public interface PostService {

	PostResponse insertPost(PostRequest post);

	List<PostResponse> getAllPost();

	PostResponse getPostById(Long id);

	PostResponse updatePost(PostRequest post);

}
