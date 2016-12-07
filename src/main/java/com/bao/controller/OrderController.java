package com.bao.controller;

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
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

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
			if (orderDetails.size()==0) throw new SystemException(500, "该订单详情为空");

			return ResponseEntity.success(orderDetails);
		} catch (SystemException e) {
			logger.error("order orderdetail", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e){
			logger.error("order orderdetail", e);
			return ResponseEntity.error("未知异常", e);
		}
	}
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

	@RequestMapping(value = "/myorder", method = RequestMethod.POST)
	public Object myorder(@RequestBody String aoData) {
		try {
			DataTableReqInfo dataTableReqInfo = reciveAoData(aoData);
			Orders orders = new Orders();

			List<Map<String, Object>> resultList = orderMapper.selectBySelective(dataTableReqInfo, orders);
			DataTableRespInfo dataTableRespInfo = new DataTableRespInfo();
			dataTableRespInfo.setAaData(resultList);
			int count = orderMapper.countBySelective(dataTableReqInfo,orders);
			dataTableRespInfo.setiTotalDisplayRecords(count);
			dataTableRespInfo.setiTotalRecords(count);
			dataTableRespInfo.setsEcho(dataTableReqInfo.getsEcho());
			return dataTableRespInfo;

		} catch (SystemException e) {
			logger.error("order myorder", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e){
			logger.error("order myorder", e);
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

	private DataTableReqInfo reciveAoData(String aoData)throws Exception{
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
		}
		return dataTableReqInfo;
	}
}
