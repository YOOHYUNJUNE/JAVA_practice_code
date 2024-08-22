package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.entity.BlogEntity;
import com.kosta.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // autowired 대체
public class BlogServiceImpl implements BlogService {
	
	private final BlogRepository br;
	
	@Override
	public BlogEntity save(BlogEntity blogEntity) throws Exception {
		return br.save(blogEntity);
		
	}

	@Override
	public List<BlogEntity> findAll() {
		return br.findAll();
	}

	@Override
	public BlogEntity findById(int id) throws Exception {
		Optional<BlogEntity> optDto = br.findById(id);
		BlogEntity blog = optDto.orElseThrow(() -> new Exception("게시물을 찾을 수 없습니다."));
		return blog;
		
	}

	@Override
	public void deleteById(int id) throws Exception {
		BlogEntity dto = findById(id);
		br.deleteById(dto.getId());
	}

	@Override
	public BlogEntity update(BlogEntity blogEntity) throws Exception {
		BlogEntity post = findById(blogEntity.getId());
		post.setName(blogEntity.getName());
		post.setContent(blogEntity.getContent());
		
		BlogEntity upPost = br.save(post);	
		return upPost;
	}

}
