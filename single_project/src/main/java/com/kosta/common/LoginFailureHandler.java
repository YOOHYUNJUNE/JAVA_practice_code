package com.kosta.common;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
	private final UserRepository userRepository;

	@Override
	@Transactional
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String nickname = request.getParameter("username");
		String err = "로그인 실패";
		Optional<User> optUser = userRepository.findByUsername(nickname);
		if (optUser.isPresent()) {
			User user = optUser.get();
			if (user.getLoginAttempt() < 5) {
				user.setLoginAttempt(user.getLoginAttempt() + 1);
				userRepository.save(user);
				err = "로그인 실패 : " + user.getLoginAttempt();
			} else {
				err = "계정 잠김 (관리자에게 문의)";
			}
		}
		request.setAttribute("err", err);
		RequestDispatcher rd = request.getRequestDispatcher("/");
		rd.forward(request, response);
	}
}
