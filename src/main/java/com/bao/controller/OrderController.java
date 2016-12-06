package com.bao.controller;

import com.bao.controller.msg.order.OrderDetailItem;
import com.bao.controller.msg.order.OrderingRequest;
import com.bao.framework.ResponseEntity;
import com.bao.framework.exception.ServiceException;
import com.bao.mapper.OrderDetailMapper;
import com.bao.mapper.OrderMapper;
import com.bao.mapper.ProductMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.OrderDetail;
import com.bao.model.Orders;
import com.bao.model.Product;
import com.bao.model.Shopper;
import com.bao.utils.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private ShopperMapper shopperMapper;
	@Autowired
	private ProductMapper productMapper;

	/*shopper use*/
	@RequestMapping(value = "/ordering", method = RequestMethod.POST)
	public ResponseEntity<?> ordering(@RequestBody OrderingRequest request) {
		try {
			orderIng(request);

			return ResponseEntity.success(true);
		} catch (ServiceException e) {
			logger.error("order ordering", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e){
			logger.error("order ordering", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	private void orderIng(OrderingRequest request)throws Exception{
		//get userId from somewhere
		long userId = 1L;
		Shopper shopper = shopperMapper.selectByPrimaryKey(userId);
		if (shopper == null) throw new ServiceException(500,"shopper不存在");
		//check request
		List<OrderDetailItem> orderDetailItems = request.getOrderDetailItems();
		if (orderDetailItems.size()==0) throw new ServiceException(500,"商品不能为空");
		if (request.getDeliveryTime()==0||request.getWeddingTime()==0)throw new ServiceException(500,"时间不能为空");

		String orderNum = OrderUtils.generatorOrderNum();
		Orders orders = new Orders();
		orders.setOrderNum(orderNum);
		orders.setShopperId(userId);
		orders.setCustomer(shopper.getContactName());
		orders.setContact(shopper.getContactWay());
		orders.setAddress(shopper.getAddress());
		orders.setDeliveryTime(new Timestamp(request.getDeliveryTime()));
		orders.setWeddingTime(new Timestamp(request.getWeddingTime()));
		orders.setOrderTime(new Timestamp(System.currentTimeMillis()));
		orders.setStatus(0);//1下单成功
		orders.setOrderPrice(BigDecimal.ZERO);

		orderMapper.insertSelective(orders);

		//check product
		List<OrderDetail> orderDetails = new ArrayList<>();
		BigDecimal orderPrice = BigDecimal.ZERO;

		for (OrderDetailItem orderDetailItem : orderDetailItems){
			if (orderDetailItem.getProductNum() <= 0 || orderDetailItem.getProductNum() >= 9999)
				throw new ServiceException(500,"商品件数不符合规则");
			Product product = productMapper.selectById(orderDetailItem.getProductId());
			if (product == null) throw new ServiceException(500,"商品不存在");
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orders.getId());
			orderDetail.setProductId(product.getId());
			orderDetail.setProductCount(orderDetailItem.getProductNum());
			orderDetails.add(orderDetail);

			orderPrice = orderPrice.add(product.getPrice().multiply(BigDecimal.valueOf(orderDetailItem.getProductNum())));
		}
		Orders forUpdate = new Orders();
		forUpdate.setId(orders.getId());
		forUpdate.setStatus(1);
		forUpdate.setOrderPrice(orderPrice);
		orderDetailMapper.batchInsert(orderDetails);
		orderMapper.updateByPrimaryKeySelective(forUpdate);
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
