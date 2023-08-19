package com.lancesoft.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;

public interface ProductsRepo extends JpaRepository<ProductsEntity, String> {
	
	Boolean existsByProdName(String pName);
	ProductsEntity findByProdName(String pName);
	List<ProductsEntity> findByCategoriesEntity(CategoriesEntity categoriesEntity);
	ProductsEntity findByProdId(String pName);
}
