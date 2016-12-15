package com.bao.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.constant.ConstantValue;
import com.bao.controller.msg.DataTableReqInfo;
import com.bao.controller.msg.DataTableRespInfo;
import com.bao.controller.msg.LoginResponse;
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
			LoginResponse loginResponse = new LoginResponse();
			manager.setPassword(DigestUtils.md5Hex(manager.getPassword()));
			Manager managerResult = managerMapper.login(manager);
			if (managerResult != null) {
				String uuid = UUID.randomUUID().toString();
				stringRedisTemplate.opsForValue().set(ConstantValue.MANAGER_TOKEN + uuid,
						String.valueOf(managerResult.getId()), 12, TimeUnit.HOURS);
				loginResponse.setToken(uuid);
				loginResponse.setLoginName(managerResult.getName());
			} else {
				return ResponseEntity.error("登录失败", loginResponse);
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

	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	public DataTableRespInfo getAllInfo(@RequestBody String aoData) throws Exception {
		try {
			JSONArray jsonArray = new JSONArray(URLDecoder.decode(aoData, "utf8").replaceAll("aoData=", ""));
			JSONObject jsonObject;
			DataTableReqInfo dataTableReqInfo = new DataTableReqInfo();

			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				if ("iDisplayLength".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setiDisplayLength(jsonObject.getInt("value"));
				}
				if ("iDisplayStart".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setiDisplayStart(jsonObject.getInt("value"));
				}
				if ("sEcho".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setsEcho(jsonObject.getInt("value"));
				}
				if ("sSearch".equals(jsonObject.getString("name"))) {
					dataTableReqInfo.setsSearch(jsonObject.getString("value"));
				}
			}
			List<Map<String, Object>> resultList = shopperMapper.getAllInfo(dataTableReqInfo);
			DataTableRespInfo dataTableRespInfo = new DataTableRespInfo();
			dataTableRespInfo.setAaData(resultList);
			int count = shopperMapper.countAllShopper(dataTableReqInfo);
			dataTableRespInfo.setiTotalDisplayRecords(count);
			dataTableRespInfo.setiTotalRecords(count);
			dataTableRespInfo.setsEcho(dataTableReqInfo.getsEcho());
			return dataTableRespInfo;
		} catch (Exception e) {
			logger.error("error to get all info", e);
			throw e;
		}
	}

	@RequestMapping(value = "/updateShopper", method = RequestMethod.POST)
	public ResponseEntity<?> updateShopper(@RequestBody Shopper shopper) {
		try {
			return ResponseEntity.success(shopperMapper.updateShopper(shopper));
		} catch (Exception e) {
			logger.error("error to update shopper", e);
			return ResponseEntity.error("修改商家失败", e);
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteShopper(@PathVariable("id") String id) {
		try {
			return ResponseEntity.success(shopperMapper.deleteById(Long.parseLong(id)));
		} catch (Exception e) {
			logger.error("error to delete shopper", e);
			return ResponseEntity.error("删除商家失败", e);
		}
	}

	@RequestMapping(value = "/getShopper/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getShopperById(@PathVariable("id") String id) {
		try {
			return ResponseEntity.success(shopperMapper.selectByPrimaryKey(Long.parseLong(id)));
		} catch (Exception e) {
			logger.error("error to  query shopper", e);
			return ResponseEntity.error("查询商家失败", e);
		}
	}

}
