package com.lancesoft.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lancesoft.dto.ProductsDto;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.payload.ForgotPasswordPayload;
import com.lancesoft.service.AdminDashboardService;
import com.lancesoft.service.ProductsService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

	@Autowired
	AdminDashboardService adminDashboardServiceImpl;

	@Autowired
	ProductsService productsServiceImpl;

	@PostMapping("/addcategory")
	public ResponseEntity addCategory(@RequestBody CategoriesEntity categoriesEntity) {
		adminDashboardServiceImpl.addCategory(categoriesEntity);
		return new ResponseEntity("data saved", HttpStatus.ACCEPTED);
	}

	@PostMapping("/addproducts")
	public ResponseEntity addProducts(@RequestBody ProductsDto productsDto) {

		return new ResponseEntity(productsServiceImpl.addProducts(productsDto), HttpStatus.ACCEPTED);
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<?> getAllProducts() {
		return adminDashboardServiceImpl.getAllProducts();
	}

	@GetMapping("/myprofile")
	public ResponseEntity<?> myProfile(HttpServletRequest httpServletRequest) {
		try {
			return adminDashboardServiceImpl.getMyProfile(httpServletRequest);
		} catch (Exception e) {
			return new ResponseEntity("", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateprofile")
	public ResponseEntity<?> updatedProfile(@RequestBody RegistrationEntity entity) {
		try {
			return adminDashboardServiceImpl.updateProfile(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("token expired", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateproduct")
	public ResponseEntity<?> updatedProduct(HttpServletRequest httpServletRequest,
			@RequestBody ProductsEntity productsEntity) {
		try {
			return adminDashboardServiceImpl.updateProduct(httpServletRequest, productsEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("token expired", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getallcategories")
	public ResponseEntity<?> getAllCategories() {
		return adminDashboardServiceImpl.getAllCategory();
	}

	@GetMapping("/getonecategory")
	public ResponseEntity<?> getoneCategory(@RequestParam("catName") String catName) {

		return adminDashboardServiceImpl.getoneCategory(catName);
	}

	@PostMapping("/updatepassword")
	public ResponseEntity<?> updatePassword(@RequestBody ForgotPasswordPayload forgotPasswordPayload) {
		System.out.println("called");
		return adminDashboardServiceImpl.forgotPassword(forgotPasswordPayload);
	}
	
	

}
