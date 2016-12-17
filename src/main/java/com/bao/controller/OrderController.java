package com.bao.controller;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.controller.msg.DataTableReqInfo;
import com.bao.controller.msg.DataTableRespInfo;
import com.bao.controller.msg.order.OrderingRequest;
import com.bao.dao.OrderDao;
import com.bao.framework.ResponseEntity;
import com.bao.framework.exception.SystemException;
import com.bao.mapper.OrderDetailMapper;
import com.bao.mapper.OrderMapper;
import com.bao.mapper.ProductMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.OrderDetail;
import com.bao.model.Orders;

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

  @RequestMapping(value = "/orderdetail/{id}", method = RequestMethod.POST)
  public ResponseEntity<?> orderdetail(@PathVariable("id") Long orderId) {
    try {
      if (orderId == null) throw new SystemException(500, "orderId不能为空");
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setOrderId(orderId);
      List<OrderDetail> orderDetails = orderDetailMapper.selectBySelective(orderDetail);
      if (orderDetails.size() == 0) throw new SystemException(500, "该订单详情为空");

      return ResponseEntity.success(orderDetails);
    } catch (SystemException e) {
      logger.error("order orderdetail", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("order orderdetail", e);
      return ResponseEntity.error("未知异常", e);
    }
  }


  @RequestMapping(value = "/ordercancel/{id}", method = RequestMethod.POST)
  public ResponseEntity<?> orderCancel(@PathVariable("id") Long orderId) {
    try {
      if (orderId == null) throw new SystemException(500, "orderId不能为空");
      Orders orders = orderMapper.selectByPrimaryKey(orderId);
      if (orders == null) throw new SystemException(500, "找不到该订单");

      if (orders.getStatus() != 0) throw new SystemException(500, "订单状态不能取消");
      orders.setStatus(4);
      orderMapper.updateByPrimaryKeySelective(orders);
      return ResponseEntity.success(true);
    } catch (SystemException e) {
      logger.error("order orderdetail", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("order orderdetail", e);
      return ResponseEntity.error("未知异常", e);
    }
  }

  /* shopper use */
  @RequestMapping(value = "/ordering", method = RequestMethod.POST)
  public ResponseEntity<?> ordering(@RequestBody OrderingRequest request) {
    try {
      orderDao.makeOrder(request);
      return ResponseEntity.success(true);
    } catch (SystemException e) {
      logger.error("order ordering", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("order ordering", e);
      return ResponseEntity.error("未知异常", e);
    }
  }

  @RequestMapping(value = "/myorder", method = RequestMethod.POST)
  public Object myorder(@RequestBody String aoData) {
    try {
      DataTableReqInfo dataTableReqInfo = reciveAoData(aoData);
      Orders orders = new Orders();

      List<Map<String, Object>> resultList =
          orderMapper.selectBySelective(dataTableReqInfo, orders);
      DataTableRespInfo dataTableRespInfo = new DataTableRespInfo();
      dataTableRespInfo.setAaData(resultList);
      int count = orderMapper.countBySelective(dataTableReqInfo, orders);
      dataTableRespInfo.setiTotalDisplayRecords(count);
      dataTableRespInfo.setiTotalRecords(count);
      dataTableRespInfo.setsEcho(dataTableReqInfo.getsEcho());
      return dataTableRespInfo;

    } catch (SystemException e) {
      logger.error("order myorder", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("order myorder", e);
      return ResponseEntity.error("未知异常", e);
    }
  }

  @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
  public Object queryAll(@RequestBody String aoData, HttpServletRequest request) {
    try {

      String managerId = String.valueOf(request.getAttribute("managerId"));
      if (StringUtils.isBlank(managerId)) {
        throw new IllegalArgumentException("参数不正确");
      }
      // 查库check



      DataTableReqInfo dataTableReqInfo = reciveAoData(aoData);
      Orders orders = new Orders();
      orders.setShopperName(request.getParameter("shopper_name"));
      String status = request.getParameter("status");
      if (StringUtils.isNoneBlank(status)) {
        orders.setStatus(Integer.parseInt(status));
      }
      orders.setCustomer(request.getParameter("customer"));
      orders.setContact(request.getParameter("contact"));
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      String os = request.getParameter("order_timeS");
      if (StringUtils.isNoneBlank(os)) {
        orders.setOrderTimeS(new Timestamp(simpleDateFormat.parse(os).getTime()));
      }
      String oe = request.getParameter("order_timeE");
      if (StringUtils.isNoneBlank(oe)) {
        orders.setOrderTimeE(new Timestamp(simpleDateFormat.parse(oe).getTime()));
      }
      String ws = request.getParameter("wedding_timeS");
      if (StringUtils.isNoneBlank(ws)) {
        orders.setWeddingTimeS(new Timestamp(simpleDateFormat.parse(ws).getTime()));
      }
      String we = request.getParameter("wedding_timeE");
      if (StringUtils.isNoneBlank(we)) {
        orders.setWeddingTimeE(new Timestamp(simpleDateFormat.parse(we).getTime()));
      }
      String ds = request.getParameter("delivery_timeS");
      if (StringUtils.isNoneBlank(ds)) {
        orders.setDeliveryTimeS(new Timestamp(simpleDateFormat.parse(ds).getTime()));
      }
      String de = request.getParameter("delivery_timeE");
      if (StringUtils.isNoneBlank(de)) {
        orders.setDeliveryTimeE(new Timestamp(simpleDateFormat.parse(de).getTime()));
      }

      List<Map<String, Object>> resultList =
          orderMapper.selectBySelective(dataTableReqInfo, orders);
      DataTableRespInfo dataTableRespInfo = new DataTableRespInfo();
      dataTableRespInfo.setAaData(resultList);
      int count = orderMapper.countBySelective(dataTableReqInfo, orders);
      dataTableRespInfo.setiTotalDisplayRecords(count);
      dataTableRespInfo.setiTotalRecords(count);
      dataTableRespInfo.setsEcho(dataTableReqInfo.getsEcho());
      return dataTableRespInfo;

    } catch (SystemException e) {
      logger.error("order myorder", e);
      return ResponseEntity.error(e.getErrorMessage(), e);
    } catch (Exception e) {
      logger.error("order myorder", e);
      return ResponseEntity.error("未知异常", e);
    }
  }



  /* manager use */

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public ResponseEntity<?> getInfo(HttpServletRequest request) {
    try {
      return ResponseEntity.success(productMapper.selectById(1));
    } catch (Exception e) {
      logger.error("error to get all order", e);
      return ResponseEntity.error("查询失败", e);
    }
  }

  private DataTableReqInfo reciveAoData(String aoData) throws Exception {
    JSONArray jsonArray =
        new JSONArray(URLDecoder.decode(aoData, "utf8").replaceAll("aoData=", ""));
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
      if ("iSortCol_0".equals(jsonObject.getString("name"))) {
        dataTableReqInfo.setiSortCol_0(jsonObject.getInt("value"));
      }
      if ("sSortDir_0".equals(jsonObject.getString("name"))) {
        dataTableReqInfo.setsSortDir_0(jsonObject.getString("value"));
      }
    }
    return dataTableReqInfo;
  }
}
