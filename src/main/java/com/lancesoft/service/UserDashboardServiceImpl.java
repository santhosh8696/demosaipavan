package com.lancesoft.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lancesoft.dao.AddressRepo;
import com.lancesoft.dao.CategoryRepo;
import com.lancesoft.dao.MyCartListRepo;
import com.lancesoft.dao.MyCartRepo;
import com.lancesoft.dao.OrdersRepo;
import com.lancesoft.dao.ProductsRepo;
import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.dto.AddToCartDto;
import com.lancesoft.dto.AddressDto;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.entity.OrdersEntity;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.jwt.JwtTokenString;
import com.lancesoft.jwt.JwtUtil;
import com.lancesoft.paytm.PaytmDetailPojo;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Header;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class UserDashboardServiceImpl implements UserDashboardService {

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
	MyCartListRepo myCartListRepo;

	@Autowired
	AddressRepo addressRepo;

	@Autowired
	OrdersRepo ordersRepo;

	@Autowired
	private PaytmDetailPojo paytmDetailPojo;

	@Autowired
	private Environment env;

	public List<ProductsEntity> searchProduct(ProductsEntity productsEntity) {
		List<ProductsEntity> prod = new ArrayList<>();
		if (productsRepo.existsByProdName(productsEntity.getProdName())) {
			prod.add(productsRepo.findByProdName(productsEntity.getProdName()));
			return prod;
		} else if (categoryRepo.existsByCatName(productsEntity.getProdName())) {
			CategoriesEntity categoriesEntity = categoryRepo.findByCatName(productsEntity.getProdName());
			prod = productsRepo.findByCategoriesEntity(categoriesEntity);
		}
		return prod;
	}

	@Override
	public ResponseEntity<?> getoneCategory(String catName) {

		CategoriesEntity categoriesEntity = categoryRepo.findByCatName(catName);
		List<ProductsEntity> entity = productsRepo.findByCategoriesEntity(categoriesEntity);
		return new ResponseEntity(entity, HttpStatus.OK);
	}

	public ResponseEntity getMyProfile(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("Authorization").substring(7);
		String userName = jwtUtil.extractUsername(token);
		if (jwtUtil.isTokenExpired(token)) {
			JwtTokenString jwtTokenString = new JwtTokenString();
			jwtTokenString.setToken("expired token");
			return new ResponseEntity(jwtTokenString, HttpStatus.BAD_REQUEST);
		} else {

			return new ResponseEntity(registrationRepo.findByUserName(userName), HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<?> addToCart(AddToCartDto addToCartDto, String userName) {
		ProductsEntity entity = productsRepo.findByProdId(addToCartDto.getProdId());
		if (entity.getStatus().equals("NotAvailable")) {
			return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
		} else {
			MyCart cart = new MyCart();
			cart.setEntity(entity);
			cart.setQty(addToCartDto.getQty());
			RegistrationEntity entity2 = registrationRepo.findByUserName(userName);
			cart.setUserId(entity2.getUserName());
			return new ResponseEntity(cartRepo.save(cart), HttpStatus.ACCEPTED);
		}
	}

	public MyCartList myCartList(MyCartList myCartList, String userName) {
		List<MyCart> cartList = cartRepo.findByUserId(userName);
		if (cartList.isEmpty()) {
			System.out.println("Your Cart is empty , Please add to cart");
		}
		long totalCartPrice = 0;
		for (MyCart myCart : cartList) {
			totalCartPrice = totalCartPrice + myCart.getEntity().getPrice() * myCart.getQty();
		}

		if (myCartListRepo.existsByUserName(userName)) {
			MyCartList mycartList = myCartListRepo.findByUserName(userName);
			String myCartListId = mycartList.getId();
			// myCartListDao.delete(mycartList);
			myCartList.setId(myCartListId);
		}

		myCartList.setUserName(userName);
		myCartList.setMyCartItems(cartList);
		myCartList.setTotalCost(totalCartPrice);
		return myCartListRepo.save(myCartList);

	}

	public void export(HttpServletResponse response,OrdersEntity ordersEntity) throws IOException {
		OrdersEntity ordersEntityList= ordersRepo.findById(ordersEntity.getOrderId()).orElse(null);
		RegistrationEntity registrationEntity=registrationRepo.findByUserName(ordersEntityList.getUserName());
		
		AddressEntity addressEntity=ordersEntityList.getAddressEntity();
		MyCartList cartList=ordersEntityList.getCartList();
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		
		Image image= Image.getInstance("omg.jpg");
		image.scaleAbsolute(120,40);
		image.setAlignment(50);
		
		
		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE);
		fontTitle.setSize(18);
		fontTitle.setColor(19, 124, 50);

		Header header = new Header("Invoice", "0");

		Paragraph paragraph = new Paragraph("Invoice #6006922 for Order # BPFVO­6012102­211215 : omg.com");
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(12);
		// fontParagraph.setColor(206,33,33);

		Paragraph paragraph2 = new Paragraph("Invoice", fontTitle);
		paragraph2.setSpacingAfter(2f);
		paragraph2.setAlignment(Paragraph.ALIGN_CENTER);

		Paragraph paragraph4 = new Paragraph("Delivery Location :");
		Paragraph paragraph5 = new Paragraph(registrationEntity.getFirstName()+" " +registrationEntity.getLastName());
		Paragraph paragraph6 = new Paragraph(addressEntity.getHouseNo() + ","+addressEntity.getLandmark());
		Paragraph paragraph7 = new Paragraph(addressEntity.getState()+","+addressEntity.getPincode());

		Paragraph paragraph3 = new Paragraph("Order ID", fontParagraph);

		Table table = new Table(2, 6);
		table.setAlignment(5);
		table.setBorder(2);
		table.setPadding(3);
		Cell cell = new Cell("Invoice No");
		table.addCell(cell);
		table.addCell(String.valueOf(ordersEntityList.getOrderId()));
		table.addCell(paragraph3);
		table.addCell(String.valueOf(ordersEntityList.getOrderId()));
		table.addCell("Slot");
		table.addCell(ordersEntityList.getDeliveryDate());
		table.addCell(new Paragraph("Final Total", fontParagraph));
		table.addCell(String.valueOf(cartList.getTotalCost())+" rs");
		table.addCell("Payment By");
		table.addCell(ordersEntityList.getPaymentMode());
		table.addCell("Amount payable");
		table.addCell(String.valueOf(cartList.getTotalCost())+" rs");
//		table.addCell("No.of items");
//		table.addCell("13");
		
		
		
		
		
		document.add(paragraph);
		document.add(paragraph2);
		document.add(image);
		document.add(paragraph4);
		document.add(paragraph5);
		document.add(paragraph6);
		document.add(paragraph7);
		document.add(table);
		
		document.close();

	}

	@Override
	public ResponseEntity placeOrder(String userName) {
		return null;
	}

	@Override
	public MyCartList updateCart(String cartId, Long qty, MyCartList cartList, String userName) {
		MyCart cart = cartRepo.findByCartId(cartId);
		System.err.println(cart);
		cart.setQty(qty);
		System.out.println(cartRepo.save(cart));
		return this.myCartList(cartList, userName);
	}

	public AddressEntity addAddress(AddressDto addressdto) {

		try {
			if (!(addressdto == null)) {
				throw new Exception("Null found in adding address");

			}

		} catch (Exception e) {

			System.out.println(e);
		}
		AddressEntity entity = new AddressEntity();
		ModelMapper mapper = new ModelMapper();
		mapper.map(addressdto, entity);
		entity.setDeafultAddress(true);
		return addressRepo.save(entity);

	}

	@Override
	public AddressEntity updateAddress(AddressDto addressdto) {
		try {
			if (addressdto == null) {
				throw new Exception("Null found in adding address");
			}

		} catch (Exception e) {

			System.out.println(e);
		}
		AddressEntity entity = new AddressEntity();
		ModelMapper mapper = new ModelMapper();
		mapper.map(addressdto, entity);
		return addressRepo.save(entity);
	}

	public AddressEntity addNewAddress(AddressDto addressdto, String userName) {
		System.out.println(addressdto);
		try {
			if (addressdto == null) {
				throw new Exception("Null found in adding address");
			}

		} catch (Exception e) {

			System.out.println(e);
		}
		AddressEntity entity = new AddressEntity();
		ModelMapper mapper = new ModelMapper();
		List<AddressEntity> addressEntities = addressRepo.findByUserName(userName);
		Iterator iterator = addressEntities.iterator();
		addressdto.setDeafultAddress(true);
		while (iterator.hasNext()) {
			AddressEntity addressEntity = (AddressEntity) iterator.next();
			addressEntity.setDeafultAddress(false);
			addressRepo.save(addressEntity);
		}
		mapper.map(addressdto, entity);
		return addressRepo.save(entity);
	}

	public String getOrderid() {
		List<OrdersEntity> entity1 = ordersRepo.findAll();
		ListIterator iterator = entity1.listIterator();
		OrdersEntity o = entity1.get(entity1.size() - 1);
		int orderid = o.getOrderId() + 1;
		return String.valueOf(orderid);
	}

	public OrdersEntity payWithCod(OrdersEntity ordersEntity, String userName) {

		if (ordersEntity.getPaymentMode().equals("COD")) {
			ordersEntity.setUserName(userName);
			ordersEntity.setCartList(myCartListRepo.findByUserName(userName));
			LocalDate date = LocalDate.now();
			ordersEntity.setDeliveryDate(date.plusDays(3).toString());
			List<AddressEntity> addressEntity = addressRepo.findByUserName(userName);
			Iterator iterator = addressEntity.iterator();
			while (iterator.hasNext()) {
				AddressEntity addressEntity2 = (AddressEntity) iterator.next();
				if (addressEntity2.isDeafultAddress() == true)
					ordersEntity.setAddressEntity(addressEntity2);
				ordersRepo.save(ordersEntity);
			}
		}
		return ordersEntity;
	}

	@Override
	public OrdersEntity payWithGateway(String userName) {

		return null;
	}

}