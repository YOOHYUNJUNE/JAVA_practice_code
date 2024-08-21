package com.kosta.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kosta.entity.Article;


@DataJpaTest // Jpa 관련 Test
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB 사용
public class BlogRepositoryTest {

	// test 준비
	@Autowired
	private BlogRepository blogRepository;
		
	// 테스트 코드 패턴 : Given(주고) - When(하고) - Then(검증)
	
	@Test
	@DisplayName("게시글 추가 테스트")
	public void saveArticleTest() {
		// Given
		Article article = Article.builder().title("test title").content("test content").build();
		
		// When
		Article savedArticle = blogRepository.save(article);
		
		// Then
		assertThat(savedArticle).isNotNull();
		assertThat(savedArticle.getId()).isNotNull();
		assertThat(savedArticle.getTitle()).isEqualTo("test title");
		assertThat(savedArticle.getContent()).isEqualTo("test content");		
	}
	
	
	@Test
	@DisplayName("게시글 전체 조회 테스트")
	public void findAllTest() {
		// Given
		Article article1 = Article.builder().title("1").content("testtest").build();
		blogRepository.save(article1);
		
		Article article2 = Article.builder().title("제목2").content("내용2").build();
		blogRepository.save(article2);
		
		
		// When
		List<Article> articleList = blogRepository.findAll();
		
		// Then
		assertThat(articleList).isNotNull();
		assertThat(articleList.size()).isGreaterThanOrEqualTo(2);
		assertThat(articleList.stream().anyMatch(article -> article.getTitle().equals("1"))).isTrue();
		assertThat(articleList.stream().anyMatch(article -> article.getContent().equals("내용2"))).isTrue();
		
	}
	
	
	@Test
	@DisplayName("특정 게시물 조회 테스트")
	public void findByIdTest() {
		// Given
		Article article = Article.builder().title("새 제목").content("새 내용").build();
		Article savedArticle = blogRepository.save(article);
		
		// When
//		Optional<Article> optArticle = blogRepository.findById(savedArticle.getId());
//		Article foundArticle = optArticle.get();
		Article foundArticle = blogRepository.findById(savedArticle.getId()).get();
		
		// Then
		assertThat(foundArticle).isNotNull();
		assertThat(foundArticle.getId()).isEqualTo(savedArticle.getId());
		assertThat(foundArticle.getTitle()).isEqualTo(savedArticle.getTitle());
		assertThat(foundArticle.getContent()).isEqualTo(savedArticle.getContent());
		
	}
	
	
	
	@Test
	@DisplayName("특정 게시물 삭제(ID)")
	public void deleteArticleById() {
		// Given
		int originalSize = blogRepository.findAll().size();
		Article article = Article.builder().title("새 제목").content("새 내용").build();
		Article savedArticle = blogRepository.save(article);
		
		// When
		blogRepository.deleteById(savedArticle.getId());
		int newSize = blogRepository.findAll().size();
		
		// Then
		assertThat(originalSize).isEqualTo(newSize);
	}
	
	
	
	@Test
	@DisplayName("게시물 수정")
	public void updateArticle() {
		// Given
		Article article = Article.builder().title("새 제목").content("새 내용").build();
		Article savedArticle = blogRepository.save(article);
		
		// When
		Article foundArticle = blogRepository.findById(savedArticle.getId()).get();
		foundArticle.setTitle("변경된 글 제목");
		foundArticle.setContent("변경된 글 내용");
	
		// Then
		Article changedArticle = blogRepository.findById(savedArticle.getId()).get();
		assertThat(foundArticle.getTitle()).isEqualTo(changedArticle.getTitle());
		assertThat(foundArticle.getContent()).isEqualTo(changedArticle.getContent());
		
	}
	
	
	
	@Test
	@DisplayName("제목 또는 내용 검색")
	public void searchByTitleOrContent() {
		// Given
		Article article1 = Article.builder().title("QWER").content("대관람차").build();
		blogRepository.save(article1);
		
		Article article2 = Article.builder().title("ㅇㅇㅇ").content("QWER").build();
		blogRepository.save(article2);
		
		
		// When
		List<Article> result = blogRepository.findByTitleContainsOrContentContains("QWER", "QWER");
			
		// Then
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(2);
		
		
	}
	
	
	
	
}
