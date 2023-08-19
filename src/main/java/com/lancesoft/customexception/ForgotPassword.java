//
//package com.lancesoft.customexception;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.lancesoft.dao.RegistrationRepo;
//import com.lancesoft.entity.RegistrationEntity;
//import com.lancesoft.payload.ForgotPasswordPayload;
//
//@Component
//public class ForgotPassword {
//	@Autowired
//	BCryptPasswordEncoder encoder;
//
//	@Autowired
//	RegistrationRepo repo;
//
//	public void setPassword(ForgotPasswordPayload payload) {
//
//		List<RegistrationEntity> entity= repo.findByPassword(payload.getPassword());
//		System.out.println((entity));
//
//		if (repo.existsByUserName(payload.getUserName())
//				&& encoder.matches(payload.getPassword(), repo.findByPassword(payload.getPassword()).toString())) {
//			System.out.println(repo.findByPassword(payload.getPassword()));
//		}
//
//	}
//
//}
