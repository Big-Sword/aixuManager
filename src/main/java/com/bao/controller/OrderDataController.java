package com.bao.controller;

import com.bao.controller.msg.orderdata.ProductResponse;
import com.bao.controller.msg.orderdata.ShopperProductResponse;
import com.bao.controller.msg.orderdata.StartEndTimeRequest;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


  @RequestMapping(value = "/product/{name}", method = RequestMethod.POST)
  public ResponseEntity<?> product(@PathVariable("name") String name,
                                   @RequestBody StartEndTimeRequest startEndTimeRequest) {
    try {
      if (StringUtils.isBlank(name)) throw new ServiceException(500, "商品名称不能为空");

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      Timestamp start = startEndTimeRequest.getStartTime()==null ? null :
              new Timestamp(simpleDateFormat.parse(startEndTimeRequest.getStartTime()).getTime());
      Timestamp end = startEndTimeRequest.getEndTime()==null ? null :
              new Timestamp(simpleDateFormat.parse(startEndTimeRequest.getEndTime()).getTime());

      List<Product> productList = productMapper.searchByName(name);
      if (productList.size() == 0) return ResponseEntity.success(new ArrayList<>());
      List<OrderDetail> orderDetailList = orderDetailMapper.selectByProductIds( productList.stream().map(n -> n.getId()).collect(Collectors.toSet()), start, end);
      if (orderDetailList.size() == 0) return ResponseEntity.success(new ArrayList<>());

      List<ProductResponse> responseList = BeanSwapUtils.swapList(productList, ProductResponse.class);
      responseList.forEach(n->{
        Integer count = orderDetailList.stream().filter(f -> f.getProductId() == n.getId())
                .map(m -> m.getProductCount())
                .reduce((sum, cost) -> sum + cost)
                .get();
        n.setNum(count);
      });
      return ResponseEntity.success(responseList);
    } catch (SystemException e) {
      logger.error("orderdata product", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("orderdata product", e);
      return ResponseEntity.error("未知异常", e);
    }
  }

  @RequestMapping(value = "/shopper/{name}", method = RequestMethod.POST)
  public ResponseEntity<?> shopper(@PathVariable("name") String name,
                                   @RequestBody StartEndTimeRequest startEndTimeRequest) {
    try {
      if (StringUtils.isBlank(name)) throw new ServiceException(500, "用户名称不能为空");
      List<ShopperProductResponse> response = new ArrayList<>();

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      Timestamp start = startEndTimeRequest.getStartTime()==null ? null :
              new Timestamp(simpleDateFormat.parse(startEndTimeRequest.getStartTime()).getTime());
      Timestamp end = startEndTimeRequest.getEndTime()==null ? null :
              new Timestamp(simpleDateFormat.parse(startEndTimeRequest.getEndTime()).getTime());

      List<Shopper> shopperList = shopperMapper.searchByName(name);
      if (shopperList.size() == 0) return ResponseEntity.success(response);
      shopperList.forEach(shopper -> {
        ShopperProductResponse shopperProductResponse = new ShopperProductResponse();
        shopperProductResponse.setName(shopper.getName());

        List<Orders> ordersList = orderMapper.selectByShopperId(shopper.getId(), start, end);
        if (ordersList.size() == 0) {
          shopperProductResponse.setProductResponses(new ArrayList<>());
        }else {
          List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderIds(ordersList.stream().map(n -> n.getId()).collect(Collectors.toSet()));
          if (orderDetailList.size() == 0){
            shopperProductResponse.setProductResponses(new ArrayList<>());
          }else {
            List<Product> productList = productMapper.selectBySetIds(orderDetailList.stream().map(n -> n.getProductId()).collect(Collectors.toSet()));
            List<ProductResponse> responseList = BeanSwapUtils.swapList(productList, ProductResponse.class);
            responseList.forEach(n->{
              Integer count = orderDetailList.stream().filter(f -> f.getProductId() == n.getId())
                      .map(m -> m.getProductCount())
                      .reduce((sum, cost) -> sum + cost)
                      .get();
              n.setNum(count);
            });

            shopperProductResponse.setProductResponses(responseList);
          }
        }
        response.add(shopperProductResponse);
      });

      return ResponseEntity.success(response);
    } catch (SystemException e) {
      logger.error("orderdata shopper", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("orderdata shopper", e);
      return ResponseEntity.error("未知异常", e);
    }
  }
}
