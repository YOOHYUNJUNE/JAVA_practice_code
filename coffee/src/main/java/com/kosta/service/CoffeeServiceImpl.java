package com.coffee.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.coffee.dao.CoffeeDAO;
import com.coffee.dao.CoffeeDAOImpl;
import com.coffee.model.Coffee;

public class CoffeeServiceImpl implements CoffeeService {
	
	CoffeeDAO coffeeDAO = new CoffeeDAOImpl();

	@Override
	// 요청 파라미터 (name, img, price, caffeine, sugar, detail)를 가지고 Coffee 객체 생성
	public void addCoffee(HttpServletRequest req) throws Exception {
		String name = req.getParameter("name");
//		String img = req.getParameter("img");
		int price = Integer.parseInt(req.getParameter("price")) ;
		int caffeine = Integer.parseInt(req.getParameter("caffeine"));
		int sugar = Integer.parseInt(req.getParameter("sugar"));
		String detail = req.getParameter("detail");
		
		// img 가져오기
		Part part = req.getPart("img");
		String header = part.getHeader("content-disposition");
		int start = header.indexOf("filename=");
		String img = header.substring(start + 10, header.length()-1);
		
		// img 저장
		if (img != null && !img.isEmpty()) {
			// StringBuilder를 이용해서, 저장되는 이미지를 날짜,시간으로 바꾸기
			StringBuilder pImg = new StringBuilder();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmSS");
			String time = dateFormat.format(cal.getTime());
			pImg.append(time).append(img.substring(img.lastIndexOf(".")));
			img = pImg.toString();
			part.write(img);
			
		} else { // 사진 등록 안하는 경우
			img = "default.jpg";
		}
		
		// Coffee 객체 생성
		Coffee coffee = new Coffee(name, img, price, caffeine, sugar, detail);
		
		// DAO에게 DB에 넣으라 명령
		coffeeDAO.addCoffee(coffee);
		
		
	}

	
	
	@Override
	public List<Coffee> getAll() throws Exception {
		return coffeeDAO.getAll();
	}

	
	
	@Override
	public Coffee getCoffee(HttpServletRequest req) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		return coffeeDAO.getCoffee(id);
	}

	
	
	@Override
	public void deleteCoffee(HttpServletRequest req) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		coffeeDAO.deleteCoffee(id);
	}

	
	
	@Override
	public void modifyCoffee(HttpServletRequest req) throws Exception {
		// id값 가져오기
		int id = Integer.parseInt(req.getParameter("id"));
		Coffee coffee = coffeeDAO.getCoffee(id);
		
//		String img = req.getParameter("img");
		String name = req.getParameter("name");
		int price = Integer.parseInt(req.getParameter("price")) ;
		int caffeine = Integer.parseInt(req.getParameter("caffeine"));
		int sugar = Integer.parseInt(req.getParameter("sugar"));
		String detail = req.getParameter("detail");
		
		
		// 수정
//		coffee.setImg(img);
		coffee.setName(name);
		coffee.setPrice(price);
		coffee.setCaffeine(caffeine);
		coffee.setSugar(sugar);
		coffee.setDetail(detail);
		
		// img 가져오기
		Part part = req.getPart("img");
		String header = part.getHeader("content-disposition");
		int start = header.indexOf("filename=");
		String img = header.substring(start + 10, header.length()-1);
		
		// img 저장
		if (img != null && !img.isEmpty()) {
			part.write(img);
			// 수정시
			coffee.setImg(img);
		}
		
		// DAO에게 DB에 넣으라 명령
		coffeeDAO.modifyCoffee(coffee);
				
	}
	

}
