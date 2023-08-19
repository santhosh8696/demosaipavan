package com.lancesoft.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.dao.OrdersRepo;
import com.lancesoft.dto.AddToCartDto;
import com.lancesoft.dto.AddressDto;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.entity.OrdersEntity;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.service.UserDashboardService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserDashboardController {

	@Autowired
	UserDashboardService userDashboardService;

	@PostMapping("/searchproduct")
	public List<ProductsEntity> searchproduct(@RequestBody ProductsEntity entity) {
		return userDashboardService.searchProduct(entity);
	}

	@GetMapping("/getonecategory")
	public ResponseEntity<?> getoneCategory(@RequestParam("catName") String catName) {
		return userDashboardService.getoneCategory(catName);
	}

	@GetMapping("/myprofile")
	public ResponseEntity<?> myProfile(HttpServletRequest httpServletRequest) {
		try {
			return userDashboardService.getMyProfile(httpServletRequest);
		} catch (Exception e) {
			return new ResponseEntity("", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addtocart")
	public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto, Authentication authentication) {
		String userName = authentication.getName();
		return userDashboardService.addToCart(addToCartDto, userName);
	}

	@PostMapping("/mycartlist")
	public MyCartList myCartList(@RequestBody MyCartList myCartList, Authentication authentication) {
		String userName = authentication.getName();
		return userDashboardService.myCartList(myCartList, userName);
	}

	@GetMapping("/pdf/generate")
	public void generatePDF(HttpServletResponse response,@RequestBody OrdersEntity entity) throws IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		userDashboardService.export(response,entity);
	}


	@PostMapping("/updatecart")
	public MyCartList updateCart(Authentication authentication, @RequestParam("qty") Long qty,
			@RequestParam String cartId, MyCartList cartList) {
		String userName = authentication.getName();
		return userDashboardService.updateCart(cartId, qty, cartList, userName);
	}

	@PostMapping("/addaddress")
	public AddressEntity addAddress(@RequestBody AddressDto addressdto) {
		return userDashboardService.addAddress(addressdto);
	}

	@PostMapping("/updateaddress")
	public AddressEntity updateaddress(@RequestBody AddressDto addressdto) {
		return userDashboardService.updateAddress(addressdto);
	}

	@PostMapping("/addnewaddress")
	public AddressEntity addNewAddress(@RequestBody AddressDto addressdto, Authentication authentication) {
		String userName = authentication.getName();
		return userDashboardService.addNewAddress(addressdto, userName);
	}

	@PostMapping("/paywithcod")
	public OrdersEntity checkout(@RequestBody OrdersEntity ordersEntity, Authentication authentication) {
		String userName = authentication.getName();
		return userDashboardService.payWithCod(ordersEntity, userName);
	}
	


}
