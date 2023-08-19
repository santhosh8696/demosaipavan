package com.lancesoft.service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.dao.AdminRegistrationDao;
import com.lancesoft.dao.CategoryRepo;
import com.lancesoft.dao.ProductsRepo;
import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.jwt.JwtTokenString;
import com.lancesoft.jwt.JwtUtil;
import com.lancesoft.payload.ForgotPasswordPayload;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
	@Autowired
	CategoryRepo repo;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	RegistrationRepo registrationRepo;

	@Autowired
	ProductsRepo productsRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	BCryptPasswordEncoder encoder;

	public void addCategory(CategoriesEntity categoriesEntity) {
		System.out.println(categoriesEntity);
		repo.save(categoriesEntity);
	}

	public ResponseEntity getMyProfile(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("Authorization").substring(7);
		String userName = jwtUtil.extractUsername(token);
		if (jwtUtil.isTokenExpired(token)) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity(registrationRepo.findByUserName(userName), HttpStatus.OK);
		}
	}

	public ResponseEntity updateProfile(RegistrationEntity registrationEntity) {
		registrationEntity.setUser_id(registrationEntity.getUser_id());
		return new ResponseEntity(registrationRepo.save(registrationEntity), HttpStatus.OK);
	}

	public ResponseEntity updateProduct(HttpServletRequest httpServletRequest, ProductsEntity productsEntity) {

		ProductsEntity entity = productsRepo.findByProdName(productsEntity.getProdName());
		productsEntity.setProdId(entity.getProdId());
		productsRepo.save(productsEntity);
		return new ResponseEntity(productsEntity, HttpStatus.OK);

	}

	public ResponseEntity getAllCategory() {
		return new ResponseEntity(categoryRepo.findAll(), HttpStatus.OK);
	}

	public ResponseEntity getAllProducts() {
		return new ResponseEntity(productsRepo.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getoneCategory(String catName) {
		CategoriesEntity categoriesEntity = categoryRepo.findByCatName(catName);
		List<ProductsEntity> entity = productsRepo.findByCategoriesEntity(categoriesEntity);
		return new ResponseEntity(entity, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> forgotPassword(ForgotPasswordPayload forgotPasswordPayload) {
		RegistrationEntity registrationEntity = registrationRepo.findByUserName(forgotPasswordPayload.getUserName());
		System.out.println(registrationEntity.getPassword());
		System.out.println(forgotPasswordPayload.getOldPassword());
		if (encoder.matches(forgotPasswordPayload.getOldPassword(), registrationEntity.getPassword())) {
			registrationEntity.setPassword(encoder.encode(forgotPasswordPayload.getNewPassword()));
			registrationRepo.save(registrationEntity);
			return new ResponseEntity(HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}
}
