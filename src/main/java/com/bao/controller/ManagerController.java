package com.bao.controller;

import com.bao.framework.ResponseEntity;
import com.bao.mapper.ManagerMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.Manager;
import com.bao.model.Shopper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerMapper managerMapper;
	@Autowired
	private ShopperMapper shopperMapper;

	@RequestMapping(value = "/common/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Manager manager, HttpServletRequest request) {
		HttpSession session = request.getSession();
		long id = managerMapper.login(manager);
		session.setAttribute("token", id);
		if (id != 0L)
			return ResponseEntity.success(true);
		return ResponseEntity.success(false);

	}

	@RequestMapping(value = "/createShopper", method = RequestMethod.POST)
	public ResponseEntity<?> createShopper(@RequestBody Shopper shopper) {
		return ResponseEntity.success(shopperMapper.createShopper(shopper));
	}

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAllInfo() {
		return ResponseEntity.success(shopperMapper.getAllInfo());
	}

}
