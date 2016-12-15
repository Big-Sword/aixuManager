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

import com.bao.constant.ConstantValue;
import com.bao.controller.msg.LoginResponse;
import com.bao.framework.ResponseEntity;
import com.bao.mapper.ShopperMapper;
import com.bao.model.Shopper;

@RestController
@RequestMapping("/shopper")
public class ShopperManagerController {

	private static final Logger logger = LoggerFactory.getLogger(ShopperManagerController.class);

	@Autowired
	private ShopperMapper shopperMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@RequestMapping(value = "/common/login", method = RequestMethod.POST)
	public ResponseEntity<?> loginShopper(@RequestBody Shopper shopper) {
		try {
			String loginName = shopper.getLoginName();
			String loginPassword = shopper.getLoginPassword();
			boolean isFirstLogin = false;
			if (StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPassword)) {
				return ResponseEntity.error("用户名和密码不能为空", null);
			}
			LoginResponse loginResponse = new LoginResponse();
			if (stringRedisTemplate.opsForValue().get("isUpdated") == null) {
				isFirstLogin = true;
				shopper.setLoginPassword(loginPassword);
			} else {
				shopper.setLoginPassword(DigestUtils.md5Hex(loginPassword));
			}
			shopper = shopperMapper.loginShopper(shopper);
			if (shopper != null) {// 登录成功
				loginResponse.setFirstLogin(isFirstLogin);
				String uuid = UUID.randomUUID().toString();
				stringRedisTemplate.opsForValue().set(ConstantValue.USER_TOKEN + uuid, String.valueOf(shopper.getId()),
						12, TimeUnit.HOURS);
				loginResponse.setToken(uuid);
				loginResponse.setLoginName(loginName);

			} else {
				return ResponseEntity.error("用户名或密码错误", null);
			}
			return ResponseEntity.success(loginResponse);
		} catch (Exception e) {
			logger.error("error to login shopper", e);
			return ResponseEntity.error("登录失败", e);
		}

	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public ResponseEntity<?> updatePassword(@RequestBody Shopper shopper, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(shopper.getLoginPassword())) {
				return ResponseEntity.error("密码不能为空", null);
			}
			String id = String.valueOf(request.getAttribute("loginId"));
			String token = String.valueOf(request.getAttribute("token"));
			shopper.setId(Long.parseLong(id));
			shopper.setLoginPassword(DigestUtils.md5Hex(shopper.getLoginPassword()));
			shopperMapper.updatePassword(shopper);
			stringRedisTemplate.opsForValue().set("isUpdated", "1");
			stringRedisTemplate.delete(ConstantValue.USER_TOKEN + token);
			return ResponseEntity.success(true);
		} catch (Exception e) {
			logger.error("error to update password", e);
			return ResponseEntity.error("修改密码失败", e);
		}

	}

}
