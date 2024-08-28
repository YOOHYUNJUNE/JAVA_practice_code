package com.kosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Ordering;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Long> {

	List<Ordering> findByCustomerId(Long userId);

}
