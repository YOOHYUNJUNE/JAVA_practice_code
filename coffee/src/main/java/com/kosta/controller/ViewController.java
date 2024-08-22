package com.coffee.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffee.model.Coffee;
import com.coffee.service.CoffeeService;
import com.coffee.service.CoffeeServiceImpl;

public class ViewController implements CoffeeController {

	CoffeeService cs = new CoffeeServiceImpl();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		try {
			String action = req.getParameter("action");

			// 기사 수정
			if (action != null && action.equals("modifyCoffee")) {
				cs.modifyCoffee(req);
				// 수정 내용이 즉시 반영되고, 해당 페이지에 남기
				res.sendRedirect("/coffee/coffeeView?id=" + req.getParameter("id"));
				return;
			}
			// 커피 상세페이지
			Coffee coffee = cs.getCoffee(req);
			req.setAttribute("coffee", coffee);

		} catch (Exception e) {
			// 오류시 coffeeList로 보냄
			res.sendRedirect("/coffee/coffeeList");
			return;
		}

		// 상세 페이지
		String view = "/WEB-INF/views/CoffeeView.jsp";
		req.getRequestDispatcher(view).forward(req, res);

	}

}
