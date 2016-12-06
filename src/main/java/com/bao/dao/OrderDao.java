package com.bao.dao;

import com.bao.controller.msg.order.OrderDetailItem;
import com.bao.controller.msg.order.OrderingRequest;
import com.bao.framework.exception.SystemException;
import com.bao.mapper.OrderDetailMapper;
import com.bao.mapper.OrderMapper;
import com.bao.mapper.ProductMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.OrderDetail;
import com.bao.model.Orders;
import com.bao.model.Product;
import com.bao.model.Shopper;
import com.bao.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanruofei on 16/12/6.
 */

@Component
@EnableTransactionManagement
public class OrderDao {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private ShopperMapper shopperMapper;
	@Autowired
	private ProductMapper productMapper;

	@Transactional(propagation = Propagation.REQUIRED)
	public void makeOrder(OrderingRequest request) {
		// get userId from somewhere
		long userId = 1L;
		Shopper shopper = shopperMapper.selectByPrimaryKey(userId);
		if (shopper == null)
			throw new SystemException(500, "shopper不存在");
		// check request
		List<OrderDetailItem> orderDetailItems = request.getOrderDetailItems();
		if (orderDetailItems.size() == 0)
			throw new SystemException(500, "商品不能为空");
		if (request.getDeliveryTime() == 0 || request.getWeddingTime() == 0)
			throw new SystemException(500, "时间不能为空");

		Orders orders = new Orders();
		orders.setOrderNum(OrderUtils.generatorOrderNum());
		orders.setShopperId(userId);
		orders.setCustomer(shopper.getContactName());
		orders.setContact(shopper.getContactWay());
		orders.setAddress(shopper.getAddress());
		orders.setDeliveryTime(new Timestamp(request.getDeliveryTime()));
		orders.setWeddingTime(new Timestamp(request.getWeddingTime()));
		orders.setOrderTime(new Timestamp(System.currentTimeMillis()));
		orders.setStatus(0);// 1下单成功
		orders.setOrderPrice(BigDecimal.ZERO);

		orderMapper.insertSelective(orders);

		// check product
		List<OrderDetail> orderDetails = new ArrayList<>();
		BigDecimal orderPrice = BigDecimal.ZERO;

		for (OrderDetailItem orderDetailItem : orderDetailItems) {
			if (orderDetailItem.getProductNum() <= 0 || orderDetailItem.getProductNum() >= 9999)
				throw new SystemException(500, "商品件数不符合规则");
			Product product = productMapper.selectById(orderDetailItem.getProductId());
			if (product == null)
				throw new SystemException(500, "商品不存在");
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orders.getId());
			orderDetail.setProductId(product.getId());
			orderDetail.setProductCount(orderDetailItem.getProductNum());
			orderDetails.add(orderDetail);

			orderPrice = orderPrice
					.add(product.getPrice().multiply(BigDecimal.valueOf(orderDetailItem.getProductNum())));
		}
		Orders forUpdate = new Orders();
		forUpdate.setId(orders.getId());
		forUpdate.setStatus(1);
		forUpdate.setOrderPrice(orderPrice);
		orderDetailMapper.batchInsert(orderDetails);
		orderMapper.updateByPrimaryKeySelective(forUpdate);
	}

}
