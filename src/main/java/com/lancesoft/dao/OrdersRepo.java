package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.OrdersEntity;

public interface OrdersRepo extends JpaRepository<OrdersEntity, Integer> {

	OrdersEntity findByUserName(String userName);

}
