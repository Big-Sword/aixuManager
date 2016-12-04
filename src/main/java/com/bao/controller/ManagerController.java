package com.bao.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.controller.msg.LoginResponse;
import com.bao.framework.ResponseEntity;
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

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@RequestMapping(value = "/common/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Manager manager) {
		LoginResponse loginResponse = new LoginResponse();
		manager.setPassword(DigestUtils.md5Hex(manager.getPassword()));
		Manager managerResult = managerMapper.login(manager);
		if (managerResult != null) {
			String uuid = UUID.randomUUID().toString();
			stringRedisTemplate.opsForValue().set("USER_TOKEN_" + uuid, String.valueOf(managerResult.getId()), 30,
					TimeUnit.MINUTES);
			loginResponse.setToken(uuid);
			loginResponse.setLoginName(managerResult.getName());
		}
		return ResponseEntity.success(loginResponse);
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
