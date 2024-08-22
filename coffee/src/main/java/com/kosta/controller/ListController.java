package com.coffee.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffee.model.Coffee;
import com.coffee.service.CoffeeService;
import com.coffee.service.CoffeeServiceImpl;

public class ListController implements CoffeeController {
	
	CoffeeService cs = new CoffeeServiceImpl();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		try {
			
			String action = req.getParameter("action");
			
			if (action != null && action.equals("addCoffee")) {
				System.out.println("제품을 추가합니다.");
				cs.addCoffee(req);
				// 메뉴 추가 후 coffeeList로 이동
				res.sendRedirect(req.getRequestURI());
				return;
				
			} else if (action != null && action.equals("deleteCoffee")) {
				System.out.println("제품을 삭제했습니다.");
				cs.deleteCoffee(req);
				// 삭제 후 coffeeList로 이동
				res.sendRedirect(req.getRequestURI());
				return;
				
			} else {
				// default
				System.out.println("전체 메뉴 페이지입니다.");
				List<Coffee> list = cs.getAll();
				req.setAttribute("coffeeList", list);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", e.getMessage());
		}
		
		String view = "/WEB-INF/views/CoffeeList.jsp";
		req.getRequestDispatcher(view).forward(req, res);
		
	}

}
