package com.coffee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.coffee.model.Coffee;
import com.coffee.util.ConnectionPool.DBPool;

public class CoffeeDAOImpl implements CoffeeDAO {

	@Override
	public void addCoffee(Coffee coffee) throws Exception {
		String sql = "insert into coffee (name, img, price, caffeine, sugar, detail) values (?,?,?,?,?,?)";
		
		try (		
			Connection conn = DBPool.getDBPool();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setString(1, coffee.getName());
			pstmt.setString(2, coffee.getImg());
			pstmt.setInt(3, coffee.getPrice());
			pstmt.setInt(4, coffee.getCaffeine());
			pstmt.setInt(5, coffee.getSugar());
			pstmt.setString(6, coffee.getDetail());
			
			pstmt.executeUpdate();
			
		}
		
	}

	@Override
	public List<Coffee> getAll() throws Exception {
		String sql = "select * from coffee";
		List<Coffee> coffeeList = new ArrayList<>();
		
		try (
			Connection conn = DBPool.getDBPool();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
//			id, name, img, price, caffeine, sugar, detail
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String img = rs.getString("img");
				int price = rs.getInt("price");
				int caffeine = rs.getInt("caffeine");
				int sugar = rs.getInt("sugar");
				String detail = rs.getString("detail");
				
				Coffee coffee = new Coffee(id, name, img, price, caffeine, sugar, detail);
				coffeeList.add(coffee);
				
			}
		}
				
		return coffeeList;
	}
	
	
	
	
	

	@Override
	public Coffee getCoffee(int id) throws Exception {
		String sql = "select * from coffee where id=?";
		Coffee coffee = null;
		try (			
			Connection conn = DBPool.getDBPool();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {


				String img = rs.getString("img");
				String name = rs.getString("name");
				int caffeine = rs.getInt("caffeine");
				int sugar = rs.getInt("sugar");
				String detail = rs.getString("detail");
				int price = rs.getInt("price");
				
				coffee = new Coffee(id, name, img, price, caffeine, sugar, detail);
			}
				
		}
		
		return coffee;
	}

	
	
	
	@Override
	public void deleteCoffee(int id) throws Exception {
		String sql = "delete from coffee where id=?";
		
		try (			
			Connection conn = DBPool.getDBPool();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		}
		
		
	}

	@Override
	public void modifyCoffee(Coffee coffee) throws Exception {
		String sql = "update coffee set img=?, name=?, price=?, caffeine=?, sugar=?, detail=? where id=?";
		
		try (			
			Connection conn = DBPool.getDBPool();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setString(1, coffee.getImg());
			pstmt.setString(2, coffee.getName());
			pstmt.setInt(3, coffee.getPrice());
			pstmt.setInt(4, coffee.getCaffeine());
			pstmt.setInt(5, coffee.getSugar());
			pstmt.setString(6, coffee.getDetail());
			pstmt.setInt(7, coffee.getId());
			
			pstmt.executeUpdate();
		}
		
		
	}

}
