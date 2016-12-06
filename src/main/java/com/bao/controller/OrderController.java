package com.bao.controller;

import com.bao.controller.msg.order.OrderingRequest;
import com.bao.dao.OrderDao;
import com.bao.framework.ResponseEntity;
import com.bao.framework.exception.SystemException;
import com.bao.mapper.OrderDetailMapper;
import com.bao.mapper.OrderMapper;
import com.bao.mapper.ProductMapper;
import com.bao.mapper.ShopperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private ShopperMapper shopperMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private OrderDao orderDao;

	/*shopper use*/
	@RequestMapping(value = "/ordering", method = RequestMethod.POST)
	public ResponseEntity<?> ordering(@RequestBody OrderingRequest request) {
		try {

			orderDao.makeOrder(request);

			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order ordering", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e){
			logger.error("order ordering", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	/*manager use*/

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getInfo(HttpServletRequest request) {
		try {
			return ResponseEntity.success(productMapper.selectById(1));
		} catch (Exception e) {
			logger.error("error to get all order", e);
			return ResponseEntity.error("查询失败", e);
		}
	}

}
