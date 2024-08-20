package com.kosta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kosta.dto.Community;
import com.kosta.service.CommunityService;

@SpringBootTest // 애플리케이션 통합 테스트
@AutoConfigureMockMvc // MockMvc 자동 구성(일반 컨트롤러 테스트)
@MybatisTest // Mybatis임을 알려줌
class KostaMybatisApplicationTests {

	@Autowired
	protected MockMvc mockMvc; // HTTP를 모방한 TEST
	
	@Autowired
	private WebApplicationContext context; // 스프링 설정과 빈을 관리하는 컨텍스트
	
	@BeforeEach
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@DisplayName("커뮤니티 리스트 테스트")
	@Test
	public void communityTest() throws Exception {
		final String url = "/community/list";
		final
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.TEXT_HTML));
		
		result
			.andExpect(status().isOk()) // 응답 상태가 200 OK인지
			.andExpect(view().name("community/communitylist")) // 반환된 뷰가 community/communitylist.html 인지
			.andExpect(model().attributeExists("list")) // 모델에 list 속성이 있는지
			.andExpect(model().attribute("list", instanceOf(List.class))) // list 모델이 List타입인지
			.andExpect(model().attribute("list", everyItem(instanceOf(Community.class)))) // list 내부 구성요소가 community DTO인지
			.andExpect(model().attribute("list", everyItem(HasPropertyWithValue.hasProperty("title", notNullValue())))) // DTO의 title이 null이 아닌지
			.andExpect(content().contentType("text/html;charset=UTF-8")); // 응답 콘텐츠가 UTF-8인지
		
	}
	
	

	
	
	
	
	
}
