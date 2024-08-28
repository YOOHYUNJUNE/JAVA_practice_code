package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.entity.Ordering;
import com.kosta.entity.User;
import com.kosta.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository or;

	// 주문 등록
	@Override
	public Ordering save(Ordering ordering, User user) {
		ordering.setCustomer(user);
		return or.save(ordering);
	}

	// 유저의 주문목록 찾기
	@Override
	public List<Ordering> findOrderByUser(Long userId) {
		return or.findByCustomerId(userId);
	}

	// 주문목록 삭제
	@Override
	public void deleteById(Long id, User user) throws Exception {
		Ordering deleteOrder = findById(id);
		or.deleteById(deleteOrder.getId());
	}

	// 주문 ID 불러오기
	private Ordering findById(Long id) throws Exception {
		Optional<Ordering> optOrder = or.findById(id);
		Ordering ordering = optOrder.orElseThrow(() -> new Exception("주문 정보가 없습니다."));
		return ordering;
	}

	
	
}
