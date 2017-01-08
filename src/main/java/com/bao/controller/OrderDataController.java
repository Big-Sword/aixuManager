package com.bao.controller;

import com.bao.controller.msg.order.OrderUpdateRequest;
import com.bao.controller.msg.orderdata.ProductResponse;
import com.bao.framework.ResponseEntity;
import com.bao.framework.exception.ServiceException;
import com.bao.framework.exception.SystemException;
import com.bao.mapper.OrderDetailMapper;
import com.bao.mapper.OrderMapper;
import com.bao.mapper.ProductMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.OrderDetail;
import com.bao.model.Orders;
import com.bao.model.Product;
import com.bao.model.Shopper;
import com.bao.utils.BeanSwapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/orderdata")
public class OrderDataController {
  private static final Logger logger = LoggerFactory.getLogger(OrderDataController.class);
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private OrderDetailMapper orderDetailMapper;
  @Autowired
  private ProductMapper productMapper;
  @Autowired
  private ShopperMapper shopperMapper;

  @RequestMapping(value = "/shopper/mine", method = RequestMethod.POST)
  public ResponseEntity<?> shoppermine(HttpServletRequest httpRequest) {
    try {
      String loginId = (String) httpRequest.getAttribute("loginId");
      Shopper shopper = shopperMapper.selectByPrimaryKey(Long.valueOf(loginId));
      if (shopper == null) {
        throw new ServiceException(500, "您还未登录");
      }

      Orders orders = new Orders();
      orders.setShopperId(shopper.getId());
      List<Orders> ordersList = orderMapper.getBySelective(orders);
      List<Orders> orderCollect = ordersList.stream().filter(f -> (f.getStatus() != -1 && f.getStatus() != 4)).collect(Collectors.toList());

      Map<Long,Integer> count = new HashMap<>();

      orderCollect.stream().forEach(n->{
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(n.getId());
        List<OrderDetail> orderDetails = orderDetailMapper.selectBySelective(orderDetail);
        orderDetails.stream().forEach(f->{
          Integer countValue = count.get(f.getProductId());
          if (countValue == null){
            count.put(f.getProductId(),f.getProductCount());
          }else {
            count.put(f.getProductId(),countValue + f.getProductCount());
          }
        });
      });

      List<Product> products = productMapper.selectBySetIds(count.keySet());
      List<ProductResponse> responseList = BeanSwapUtils.swapList(products, ProductResponse.class);
      responseList.parallelStream().forEach(n-> n.setNum(count.get(n.getId())));

      return ResponseEntity.success(responseList);
    } catch (SystemException e) {
      logger.error("orderdata shoppermine", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("orderdata shoppermine", e);
      return ResponseEntity.error("未知异常", e);
    }
  }

  @RequestMapping(value = "/shopper/{id}", method = RequestMethod.POST)
  public ResponseEntity<?> shopper(@PathVariable("id") Long shopperId) {
    try {
      Shopper shopper = shopperMapper.selectByPrimaryKey(shopperId);
      if (shopper == null) {
        throw new ServiceException(500, "找不到该shopper");
      }

      Orders orders = new Orders();
      orders.setShopperId(shopper.getId());
      List<Orders> ordersList = orderMapper.getBySelective(orders);
      List<Orders> orderCollect = ordersList.stream().filter(f -> (f.getStatus() != -1 && f.getStatus() != 4)).collect(Collectors.toList());

      Map<Long,Integer> count = new HashMap<>();

      orderCollect.stream().forEach(n->{
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(n.getId());
        List<OrderDetail> orderDetails = orderDetailMapper.selectBySelective(orderDetail);
        orderDetails.stream().forEach(f->{
          Integer countValue = count.get(f.getProductId());
          if (countValue == null){
            count.put(f.getProductId(),f.getProductCount());
          }else {
            count.put(f.getProductId(),countValue + f.getProductCount());
          }
        });
      });

      List<Product> products = productMapper.selectBySetIds(count.keySet());
      List<ProductResponse> responseList = BeanSwapUtils.swapList(products, ProductResponse.class);
      responseList.parallelStream().forEach(n-> n.setNum(count.get(n.getId())));

      return ResponseEntity.success(responseList);
    } catch (SystemException e) {
      logger.error("orderdata shopper", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("orderdata shopper", e);
      return ResponseEntity.error("未知异常", e);
    }
  }

}
