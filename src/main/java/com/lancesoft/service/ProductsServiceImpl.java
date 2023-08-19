package com.lancesoft.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lancesoft.dao.CategoryRepo;
import com.lancesoft.dao.ProductsRepo;
import com.lancesoft.dto.ProductsDto;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;



@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	ProductsRepo productsRepo;

	@Autowired
	CategoryRepo categoryRepo;

	public ResponseEntity addProducts(ProductsDto productsDto) {
		ProductsEntity productsEntity = new ProductsEntity();
		ModelMapper mapper = new ModelMapper();
		
		if (productsDto == null ) {
			
			System.out.println("product is null");
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
			
		}else if(productsRepo
				.existsByProdName(productsDto.getProdName())) {
			System.out.println("product already exists");
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		else {
			mapper.map(productsDto, productsEntity);
			CategoriesEntity categoriesEntity = categoryRepo
					.findByCatName(productsEntity.getCategoriesEntity().getCatName());
			productsEntity.setCategoriesEntity(categoriesEntity);
			System.out.println(productsEntity);
			productsRepo.save(productsEntity);
			return new ResponseEntity(this.getAllProducts(),HttpStatus.CREATED);
			
		} 
	}
	
	public List<ProductsEntity> getAllProducts() {
		List<ProductsEntity> list=productsRepo.findAll();
		System.err.println(list);
		return productsRepo.findAll();
	}
	
}
