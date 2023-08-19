package com.lancesoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.CategoriesEntity;

public interface CategoryRepo extends JpaRepository<CategoriesEntity, String> {
	CategoriesEntity findByCatName(String catName);
	boolean existsByCatName(String catName);
}
