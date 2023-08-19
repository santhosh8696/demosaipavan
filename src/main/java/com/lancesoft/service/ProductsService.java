package com.lancesoft.service;

import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lancesoft.dto.ProductsDto;
import com.lancesoft.entity.ProductsEntity;



@Service
public interface ProductsService {

	ResponseEntity<?> addProducts(ProductsDto productsDto);

	 List<ProductsEntity> getAllProducts();
}
