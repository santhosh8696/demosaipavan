
package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.MyCart;

public interface MyCartRepo extends JpaRepository<MyCart, String> {

	List<MyCart> findByUserId(String userName);

	MyCart findByCartId(String userName);

}
