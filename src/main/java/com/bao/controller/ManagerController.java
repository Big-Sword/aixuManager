package com.bao.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.controller.msg.AllShopperResponse;
import com.bao.controller.msg.LoginResponse;
import com.bao.controller.msg.PageRequest;
import com.bao.controller.msg.ShopperResponse;
import com.bao.framework.ResponseEntity;
import com.bao.mapper.ManagerMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.Manager;
import com.bao.model.Shopper;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	private ManagerMapper managerMapper;
	@Autowired
	private ShopperMapper shopperMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@RequestMapping(value = "/common/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Manager manager) {
		try {
			if (StringUtils.isBlank(manager.getName()) || StringUtils.isBlank(manager.getPassword())) {
				return ResponseEntity.error("用户名或密码不能为空", null);
			}
			LoginResponse loginResponse = new LoginResponse();
			manager.setPassword(DigestUtils.md5Hex(manager.getPassword()));
			Manager managerResult = managerMapper.login(manager);
			if (managerResult != null) {
				String uuid = UUID.randomUUID().toString();
				stringRedisTemplate.opsForValue().set("USER_TOKEN_" + uuid, String.valueOf(managerResult.getId()), 12,
						TimeUnit.HOURS);
				loginResponse.setToken(uuid);
				loginResponse.setLoginName(managerResult.getName());
			} else {
				return ResponseEntity.error("用户名或密码错误", loginResponse);
			}
			return ResponseEntity.success(loginResponse);
		} catch (Exception e) {
			logger.error("error to login", e);
			return ResponseEntity.error("登录失败", e);
		}
	}

	@RequestMapping(value = "/createShopper", method = RequestMethod.POST)
	public ResponseEntity<?> createShopper(@RequestBody Shopper shopper) {
		try {
			if (StringUtils.isBlank(shopper.getName())) {
				return ResponseEntity.error("公司名称不能为空", null);
			}
			if (StringUtils.isBlank(shopper.getAddress())) {
				return ResponseEntity.error("公司地址不能为空", null);
			}
			if (StringUtils.isBlank(shopper.getContactName())) {
				return ResponseEntity.error("联系人名称不能为空", null);
			}
			if (StringUtils.isBlank(shopper.getContactWay())) {
				return ResponseEntity.error("联系方式不能为空", null);
			}
			ShopperResponse createShopperResponse = shopperMapper.createShopper(shopper);
			if (createShopperResponse == null) {
				return ResponseEntity.error("创建商家失败", createShopperResponse);
			}
			return ResponseEntity.success(createShopperResponse);
		} catch (Exception e) {
			logger.error("error to create shopper", e);
			return ResponseEntity.error("创建商家失败", e);
		}
	}

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAllInfo(HttpServletRequest request) {
		try {
			if (request.getParameter("pageIndex") == null || request.getParameter("pageSize") == null) {
				return ResponseEntity.error("pageIndex 和 pageSize 不能为空", null);
			}
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			PageRequest pageRequest = new PageRequest();
			pageRequest.setPageIndex(pageIndex*pageSize);
			pageRequest.setPageSize(pageSize);
			AllShopperResponse response = new AllShopperResponse();
			response.setShopperInfos(shopperMapper.getAllInfo(pageRequest));
			response.setTotal(shopperMapper.countAllShopper());
			return ResponseEntity.success(response);
		} catch (Exception e) {
			logger.error("error to get all info", e);
			return ResponseEntity.error("查询失败", e);
		}
	}

}
