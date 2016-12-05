package com.bao.controller;

import com.bao.framework.ResponseEntity;
import com.bao.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderMapper orderMapper;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getInfo(HttpServletRequest request) {
		try {
			return ResponseEntity.success(orderMapper.selectByPrimaryKey(1L));
		} catch (Exception e) {
			logger.error("error to get all order", e);
			return ResponseEntity.error("查询失败", e);
		}
	}

}
