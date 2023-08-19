package com.lancesoft.paytm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lancesoft.dao.AddressRepo;
import com.lancesoft.dao.CategoryRepo;
import com.lancesoft.dao.MyCartListRepo;
import com.lancesoft.dao.MyCartRepo;
import com.lancesoft.dao.OrdersRepo;
import com.lancesoft.dao.ProductsRepo;
import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.entity.OrdersEntity;
import com.lancesoft.jwt.JwtUtil;
import com.lancesoft.service.UserDashboardService;
import com.paytm.pg.merchant.PaytmChecksum;

@CrossOrigin("*")
@Controller

public class PaymentController {

	@Autowired
	private PaytmDetailPojo paytmDetailPojo;
	@Autowired
	private Environment env;

	@Autowired
	UserDashboardService dashboardService;

	@Autowired
	MyCartListRepo myCartListRepo;

	@Autowired
	OrdersRepo ordersRepo;

	@Autowired
	ProductsRepo productsRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	RegistrationRepo registrationRepo;

	@Autowired
	MyCartRepo cartRepo;

	@Autowired
	AddressRepo addressRepo;

	@PostMapping(value = "/submitPaymentDetail")
	@ResponseBody
	public TreeMap<String, String> getRedirect(Authentication authentication) throws Exception {
		String userName = "sri@gmail.com";
		MyCartList cartList = myCartListRepo.findByUserName(userName);
		OrdersEntity ordersEntity = new OrdersEntity();
		ordersEntity.setAmount(String.valueOf(cartList.getTotalCost()));
		ordersEntity.setPaymentStatus("NotPaid");
		ordersEntity.setPaymentMode("Paytm");
		ordersRepo.save(ordersEntity);
		OrdersEntity entity2 = ordersRepo.findByUserName(userName);
		ordersEntity.setUserName(userName);
		ordersRepo.save(ordersEntity);

		ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetailPojo.getPaytmUrl());
		TreeMap<String, String> parameters = new TreeMap<>();
		paytmDetailPojo.getDetails().forEach((k, v) -> parameters.put(k, v));
		parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
		parameters.put("EMAIL", env.getProperty("paytm.email"));
		parameters.put("ORDER_ID", String.valueOf(ordersEntity.getOrderId()));
		parameters.put("TXN_AMOUNT", ordersEntity.getAmount());
		parameters.put("CUST_ID", ordersEntity.getUserName());
		String checkSum = getCheckSum(parameters);
		parameters.put("CHECKSUMHASH", checkSum);
		modelAndView.addAllObjects(parameters);
		return parameters;
	}

	Map<String, String> mapData2 = new HashMap<>();

	@PostMapping(value = "/pgresponse")
	public ModelAndView getResponseRedirect(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		ModelAndView model2 = new ModelAndView("redirect:http://10.81.4.211:3001/receipt");

		try {

			Map<String, String[]> mapData = request.getParameterMap();
			TreeMap<String, String> parameters = new TreeMap<String, String>();
			String paytmChecksum = "";
			for (Entry<String, String[]> requestParamsEntry : mapData.entrySet()) {
				if ("CHECKSUMHASH".equalsIgnoreCase(requestParamsEntry.getKey())) {
					paytmChecksum = requestParamsEntry.getValue()[0];
				} else {
					parameters.put(requestParamsEntry.getKey(), requestParamsEntry.getValue()[0]);
				}
			}
			String result;

			boolean isValideChecksum = false;
			System.out.println("RESULT : " + parameters.toString());
			try {
				isValideChecksum = validateCheckSum(parameters, paytmChecksum);
				if (isValideChecksum && parameters.containsKey("RESPCODE")) {
					if (parameters.get("RESPCODE").equals("01")) {
						result = "Payment Successful";
					} else {
						result = "Payment Failed";
					}
				} else {
					result = "Checksum mismatched";
				}
			} catch (Exception e) {
				result = e.toString();
			}
			model.addObject("result", result);
			parameters.remove("CHECKSUMHASH");
			model.addObject("parameters", parameters);
			System.err.println(parameters);
			mapData2.putAll(parameters);
			return model2;
		} catch (Exception e) {

		}
		return model;
	}

	private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
		return PaytmChecksum.verifySignature(parameters, paytmDetailPojo.getMerchantKey(), paytmChecksum);
	}

	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return PaytmChecksum.generateSignature(parameters, paytmDetailPojo.getMerchantKey());
	}

	@PostMapping("/getResponse")
	@ResponseBody
	public Map<String, String> getResponseRedirec() {
		return mapData2;
	}

	@PostMapping("/validatepayment")
	@ResponseBody
	public OrdersEntity validatePayment(Authentication authentication) {
		String userName = authentication.getName();
		OrdersEntity entity=ordersRepo.findByUserName(userName);
		List<OrdersEntity> entityList = new ArrayList<>();
			entityList.add(entity);
		if(mapData2.get("STATUS").equals("TXN_SUCCESS")) {
			entity.setPaymentStatus("Paid");
			ordersRepo.save(entity);
		}
		else {
			ordersRepo.deleteAll(entityList);
		}
		return entity;
	}

}
