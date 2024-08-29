package com.kosta.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.entity.Product;
import com.kosta.entity.User;
import com.kosta.repository.ProductRepository;
import com.kosta.service.ProductService;
import com.kosta.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
	
	// admin(관리자 고유 역할)
	// 유저 전체 목록, 상품 추가,수정,삭제
	// 상품 상세보기X, 유저 상세X
	// @AuthenticationPrincipal User user : 인증된(로그인) 사용자 정보를 사용

	private final UserService us;
	private final ProductService ps;
	private final ProductRepository pr;
	
	// 유저 전체 페이지
	@GetMapping("/userlist")
	public String userList(Model model) {
		List<User> userList = us.findAllUser();
		model.addAttribute("user", userList);
		return "admin/userlist";
	}
	
		
	// 상품 추가 페이지
	@GetMapping("/add")
	public String addPage() {
		return "product/form";
	}
	
	// 상품 추가(이미지)
	@PostMapping("/add")
	public String addProduct(
	    @ModelAttribute Product product,
	    @RequestParam("image") MultipartFile file, // 이미지 파일 추가
	    @AuthenticationPrincipal User user) throws IOException {
	    
	    if (!file.isEmpty()) {
	        // 이미지 파일 저장 로직
	        String uploadDir = "C:/upload/temp/";
	        String fileName = file.getOriginalFilename();
	        Path uploadPath = Paths.get(uploadDir);
	        
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	        
	        try (InputStream inputStream = file.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	            
	            // 저장된 파일명을 Product 객체에 설정
	            product.setImageFileName(fileName);
	        }
	    }
	    
	    // 상품 저장 로직
	    ps.save(product, user);
	    
	    return "redirect:/list";
	}


	
	
	// 상품 수정 페이지
	@GetMapping("/modify/{id}")
	public String ModifyPage(@PathVariable("id") Long id, Model model) throws Exception {
		Product product = ps.findById(id);
		model.addAttribute("product", product);
		return "product/form";
	}
	
	
	// 상품 수정
	@PatchMapping("/modify")
	public String modifyProduct(Product product, Model model, @AuthenticationPrincipal User user) throws Exception {
		ps.update(product, user);
		return "redirect:/detail/" + product.getId();
	}
	
	// 상품 삭제
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) throws Exception {

		ps.deleteById(id, user);
		return "redirect:/list";
	}
	
	
	
	
	
	
}

