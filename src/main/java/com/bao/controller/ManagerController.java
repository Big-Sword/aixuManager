package com.bao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.controller.msg.ShopperResponse;
import com.bao.mapper.ManagerMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.Manager;
import com.bao.model.Shopper;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerMapper managerMapper;
	@Autowired
	private ShopperMapper shopperMapper;

	@RequestMapping(value = "/common/login", method = RequestMethod.POST)
	public boolean login(@RequestBody Manager manager, HttpServletRequest request) {
		HttpSession session = request.getSession();
		long id = managerMapper.login(manager);
		session.setAttribute("token", id);
		if (id != 0L)
			return true;
		return false;

	}

	@RequestMapping(value = "/createShopper", method = RequestMethod.POST)
	public ShopperResponse createShopper(@RequestBody Shopper shopper) {
		return shopperMapper.createShopper(shopper);
	}

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public List<Shopper> getAllInfo() {
		return shopperMapper.getAllInfo();
	}

}
