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
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
  public long makeOrder(OrderingRequest request,String loginId) {
    // get userId from somewhere
    long userId = NumberUtils.toLong(loginId);
    Shopper shopper = shopperMapper.selectByPrimaryKey(userId);
    if (shopper == null) throw new SystemException(500, "shopper不存在");
    // check request
    List<OrderDetailItem> orderDetailItems = request.getOrderDetailItems();
    if (orderDetailItems.size() == 0) throw new SystemException(500, "商品不能为空");
    if (request.getDeliveryTime() == 0 || request.getWeddingTime() == 0)
      throw new SystemException(500, "时间不能为空");
    List<Long> productIds = orderDetailItems.stream().map(n -> n.getProductId()).distinct()
        .collect(Collectors.toList());
    if (productIds.size() != orderDetailItems.size()) throw new SystemException(500, "请合并相同商品下单");

    Orders orders = new Orders();
    orders.setOrderNum(OrderUtils.generatorOrderNum());
    orders.setShopperId(userId);
    orders.setCustomer(request.getCustomer());
    orders.setContact(request.getContact());
    orders.setAddress(request.getAddress());
    orders.setShopperName(shopper.getName());
    orders.setDeliveryTime(new Timestamp(request.getDeliveryTime()));
    orders.setWeddingTime(new Timestamp(request.getWeddingTime()));
    orders.setOrderTime(new Timestamp(System.currentTimeMillis()));
    orders.setStatus(-2);// -1预下单 0下单成功待确定
    orders.setOrderPrice(BigDecimal.ZERO);

    orderMapper.insertSelective(orders);

    // check product
    List<OrderDetail> orderDetails = new ArrayList<>();
    BigDecimal orderPrice = BigDecimal.ZERO;

    for (OrderDetailItem orderDetailItem : orderDetailItems) {
      if (orderDetailItem.getProductNum() <= 0 || orderDetailItem.getProductNum() >= 9999)
        throw new SystemException(500, "商品件数不符合规则");
      Product product = productMapper.selectById(orderDetailItem.getProductId());
      if (product == null) throw new SystemException(500, "商品不存在");
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setOrderId(orders.getId());
      orderDetail.setProductId(product.getId());
      orderDetail.setProductCount(orderDetailItem.getProductNum());
      orderDetail.setName(product.getName());
      orderDetail.setPicUrl(product.getPicUrl());
      orderDetail.setContent(product.getContent());
      orderDetail.setPrice(product.getPrice());
      orderDetail.setModelType(product.getModelType());
      orderDetail.setColourType(product.getColourType());

      orderDetails.add(orderDetail);

      orderPrice = orderPrice
          .add(product.getPrice().multiply(BigDecimal.valueOf(orderDetailItem.getProductNum())));
    }
    Orders forUpdate = new Orders();
    forUpdate.setId(orders.getId());
    forUpdate.setStatus(-1); //-1预下单 0下单成功待确定
    forUpdate.setOrderPrice(orderPrice);
    orderDetailMapper.batchInsert(orderDetails);
    orderMapper.updateByPrimaryKeySelective(forUpdate);

    return orders.getId();
  }
}
