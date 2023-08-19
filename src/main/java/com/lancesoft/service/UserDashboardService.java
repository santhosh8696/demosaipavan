package com.lancesoft.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.lancesoft.dto.AddToCartDto;
import com.lancesoft.dto.AddressDto;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.entity.OrdersEntity;
import com.lancesoft.entity.ProductsEntity;

public interface UserDashboardService {

	List<ProductsEntity> searchProduct(ProductsEntity entity);

	ResponseEntity<?> getoneCategory(String catName);

	ResponseEntity<?> getMyProfile(HttpServletRequest httpServletRequest);

	ResponseEntity<?> addToCart(AddToCartDto addToCartDto, String userName);

	MyCartList myCartList(MyCartList myCartList, String userName);

	void export(HttpServletResponse response, OrdersEntity entity) throws IOException;

	ResponseEntity placeOrder(String userName);


	MyCartList updateCart(String userName, Long qty, MyCartList cartList, String userName2);

	AddressEntity updateAddress(AddressDto addressdto);

	AddressEntity addNewAddress(AddressDto addressdto, String userName);

	AddressEntity addAddress(AddressDto addressdto);

	OrdersEntity payWithCod(OrdersEntity checkout, String userName);

	OrdersEntity payWithGateway(String userName);

}
