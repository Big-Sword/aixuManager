package com.bao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public boolean login(@RequestBody Manager manager,HttpRequest request) {
		return managerMapper.login(manager);

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
