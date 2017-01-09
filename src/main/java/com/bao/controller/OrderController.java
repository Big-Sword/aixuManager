package com.bao.controller;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.constant.ConstantValue;
import com.bao.controller.msg.Cart;
import com.bao.controller.msg.DataTableReqInfo;
import com.bao.controller.msg.DataTableRespInfo;
import com.bao.controller.msg.order.OrderUpdateRequest;
import com.bao.controller.msg.order.OrderingRequest;
import com.bao.controller.msg.order.OrderingResponse;
import com.bao.dao.OrderDao;
import com.bao.framework.ResponseEntity;
import com.bao.framework.exception.ServiceException;
import com.bao.framework.exception.SystemException;
import com.bao.mapper.OrderDetailMapper;
import com.bao.mapper.OrderMapper;
import com.bao.mapper.OrderPaymentInfoMapper;
import com.bao.mapper.ProductMapper;
import com.bao.mapper.ShopperMapper;
import com.bao.model.OrderDetail;
import com.bao.model.OrderPaymentInfo;
import com.bao.model.Orders;
import com.bao.model.Product;
import com.bao.model.Shopper;

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
	private ProductMapper productMapper;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private ShopperMapper shopperMapper;
	@Autowired
	private OrderPaymentInfoMapper orderPatmentInfoMapper;

	@RequestMapping(value = "/orderdetail/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> orderdetail(@PathVariable("id") Long orderId) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orderId);
			List<OrderDetail> orderDetails = orderDetailMapper.selectBySelective(orderDetail);
			if (orderDetails.size() == 0)
				throw new SystemException(500, "该订单详情为空");

			return ResponseEntity.success(orderDetails);
		} catch (SystemException e) {
			logger.error("order orderdetail", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order orderdetail", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	// 2 修改 只能修改购买数量 修改完状态变成 未确认 已确认和未确认 都能修改购买数量
	@RequestMapping(value = "/orderupdate/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> orderupdate(@PathVariable("id") Long orderId, @RequestBody OrderUpdateRequest request,
			HttpServletRequest httpRequest) {
		try {
			String loginId = (String) httpRequest.getAttribute("loginId");
			Shopper shopper = shopperMapper.selectByPrimaryKey(Long.valueOf(loginId));
			if (shopper == null) {
				throw new ServiceException(500, "您还未登录");
			}
			Orders orders = new Orders();
			orders.setShopperId(shopper.getId());
			orders.setId(orderId);
			List<Orders> myorders = orderMapper.getBySelective(orders);
			if (myorders.size() == 0)
				throw new ServiceException(500, "您只能修改自己的订单");

			orderDao.updateOrder(request, orderId);

			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order orderupdate", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order orderupdate", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	// 取消订单 商家用
	@RequestMapping(value = "/ordercancel/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> orderCancel(@PathVariable("id") Long orderId, HttpServletRequest httpRequest) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			Orders orders1 = orderMapper.selectByPrimaryKey(orderId);
			if (orders1 == null)
				throw new SystemException(500, "找不到该订单");

			String loginId = (String) httpRequest.getAttribute("loginId");
			Shopper shopper = shopperMapper.selectByPrimaryKey(Long.valueOf(loginId));
			if (shopper == null) {
				throw new ServiceException(500, "您还未登录");
			}
			Orders orders = new Orders();
			orders.setShopperId(shopper.getId());
			orders.setId(orderId);
			List<Orders> myorders = orderMapper.getBySelective(orders);
			if (myorders.size() == 0)
				throw new ServiceException(500, "您只能取消自己的订单");

			if (orders.getStatus() != 0)
				throw new SystemException(500, "订单状态不能取消");
			orders.setStatus(4);
			orderMapper.updateByPrimaryKeySelective(orders);
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order ordercancel", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order ordercancel", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	// 预下单 下单
	@RequestMapping(value = "/ordercheck/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> ordercheck(@PathVariable("id") Long orderId, HttpServletRequest request) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			Orders orders = orderMapper.selectByPrimaryKey(orderId);
			if (orders == null)
				throw new SystemException(500, "找不到该订单");

			if (orders.getStatus() != -1)
				throw new SystemException(500, "订单状态不能确认");

			String loginId = (String) request.getAttribute("loginId");

			Shopper shopper = shopperMapper.selectByPrimaryKey(Long.valueOf(loginId));

			if (shopper == null) {
				throw new ServiceException(500, "您还未登录");
			}
			orders.setStatus(0);
			orderMapper.updateByPrimaryKeySelective(orders);
			// 删除购物车
			OrderDetail record = new OrderDetail();
			record.setOrderId(orders.getId());
			List<OrderDetail> orderDetails = orderDetailMapper.selectBySelective(record);
			for (OrderDetail orderDetail : orderDetails) {
				stringRedisTemplate.opsForHash().delete(ConstantValue.CART + shopper.getId(),
						String.valueOf(orderDetail.getProductId()));
			}
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order ordercheck", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order ordercheck", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	/* shopper use */
	@RequestMapping(value = "/ordering", method = RequestMethod.POST)
	public ResponseEntity<?> ordering(@RequestBody OrderingRequest request, HttpServletRequest httpRequest) {
		try {
			String loginId = String.valueOf(httpRequest.getAttribute("loginId"));
			if (StringUtils.isBlank(loginId)) {
				throw new IllegalArgumentException("loginId参数不正确");
			}

			OrderingResponse response = new OrderingResponse();

			long orderId = orderDao.makeOrder(request, loginId);// 预下单
			Orders orders = orderMapper.selectByPrimaryKey(orderId);
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(orderId);
			List<OrderDetail> orderDetails = orderDetailMapper.selectBySelective(orderDetail);

			response.setOrders(orders);
			response.setOrderDetails(orderDetails);

			return ResponseEntity.success(response);
		} catch (SystemException e) {
			logger.error("order ordering", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order ordering", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	@RequestMapping(value = "/myorder", method = RequestMethod.POST)
	public Object myorder(@RequestBody String aoData, HttpServletRequest httpRequest) {
		try {
			String loginId = String.valueOf(httpRequest.getAttribute("loginId"));
			if (StringUtils.isBlank(loginId)) {
				throw new IllegalArgumentException("loginId参数不正确");
			}

			DataTableReqInfo dataTableReqInfo = reciveAoData(aoData);
			Orders orders = new Orders();
			orders.setShopperId(NumberUtils.toLong(loginId));

			List<Map<String, Object>> resultList = orderMapper.selectBySelective(dataTableReqInfo, orders);
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

			List<Map<String, Object>> resultList = orderMapper.selectBySelective(dataTableReqInfo, orders);
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

	// 1确认 只有待确认的状态才能确认
	@RequestMapping(value = "/orderconfirm/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> orderConfirm(@PathVariable("id") Long orderId) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			Orders orders = orderMapper.selectByPrimaryKey(orderId);
			if (orders == null)
				throw new SystemException(500, "找不到该订单");

			if (orders.getStatus() != 0)
				throw new SystemException(500, "订单状态不能确认");
			orders.setStatus(1);
			orderMapper.updateByPrimaryKeySelective(orders);
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order orderconfirm", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order orderconfirm", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	// 3 发货 只有已确认的才能发货
	@RequestMapping(value = "/orderdispatch/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> orderdispatch(@PathVariable("id") Long orderId) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			Orders orders = orderMapper.selectByPrimaryKey(orderId);
			if (orders == null)
				throw new SystemException(500, "找不到该订单");

			if (orders.getStatus() != 1)
				throw new SystemException(500, "订单状态不能发货");
			orders.setStatus(2);
			orderMapper.updateByPrimaryKeySelective(orders);
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order orderconfirm", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order orderconfirm", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	// 4 完成 只有已发货的才能完成
	@RequestMapping(value = "/orderfinish/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> orderfinish(@PathVariable("id") Long orderId) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			Orders orders = orderMapper.selectByPrimaryKey(orderId);
			if (orders == null)
				throw new SystemException(500, "找不到该订单");

			if (orders.getStatus() != 2)
				throw new SystemException(500, "订单状态不能完成");
			orders.setStatus(3);
			orderMapper.updateByPrimaryKeySelective(orders);
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order orderconfirm", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order orderconfirm", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	// 5结清 只有已完成的才能结清
	@RequestMapping(value = "/ordersettleup/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> ordersettleup(@PathVariable("id") Long orderId) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			Orders orders = orderMapper.selectByPrimaryKey(orderId);
			if (orders == null)
				throw new SystemException(500, "找不到该订单");

			if (orders.getStatus() != 3)
				throw new SystemException(500, "订单状态不能结清");
			orders.setStatus(5);
			orderMapper.updateByPrimaryKeySelective(orders);
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order ordersettleup", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order ordersettleup", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	// 取消订单 后台用
	@RequestMapping(value = "/manageordercancel/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> manageordercancel(@PathVariable("id") Long orderId, HttpServletRequest httpRequest) {
		try {
			if (orderId == null)
				throw new SystemException(500, "orderId不能为空");
			Orders orders = orderMapper.selectByPrimaryKey(orderId);
			if (orders == null)
				throw new SystemException(500, "找不到该订单");

			if (orders.getStatus() != 0 && orders.getStatus() != 1)
				throw new SystemException(500, "订单状态不能取消");
			orders.setStatus(4);
			orderMapper.updateByPrimaryKeySelective(orders);
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("order ordercancel", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("order ordercancel", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	private DataTableReqInfo reciveAoData(String aoData) throws Exception {
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
			if ("iSortCol_0".equals(jsonObject.getString("name"))) {
				dataTableReqInfo.setiSortCol_0(jsonObject.getInt("value"));
			}
			if ("sSortDir_0".equals(jsonObject.getString("name"))) {
				dataTableReqInfo.setsSortDir_0(jsonObject.getString("value"));
			}
		}
		return dataTableReqInfo;
	}

	@RequestMapping(value = "/cart/{id}/{count}", method = RequestMethod.POST)
	public ResponseEntity<?> cart(@PathVariable("id") long productId, @PathVariable("count") int count,
			HttpServletRequest request) {
		try {
			String loginId = (String) request.getAttribute("loginId");

			Shopper shopper = shopperMapper.selectByPrimaryKey(Long.valueOf(loginId));

			if (shopper == null) {
				throw new ServiceException(500, "您还未登录");
			}
			if (productId <= 0)
				throw new ServiceException(500, "产品不存在");
			Product product = productMapper.selectById(productId);
			if (product == null)
				throw new ServiceException(500, "产品不存在");
			if (count <= 0)
				throw new ServiceException(500, "至少购买一件产品");

			stringRedisTemplate.opsForHash().put(ConstantValue.CART + shopper.getId(), String.valueOf(productId),
					String.valueOf(count));
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("cart", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("cart", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	@RequestMapping(value = "/myCart", method = RequestMethod.GET)
	public ResponseEntity<?> myCart(HttpServletRequest request) {
		try {
			String loginId = (String) request.getAttribute("loginId");
			List<Cart> carts = new ArrayList<>();
			Shopper shopper = shopperMapper.selectByPrimaryKey(Long.valueOf(loginId));

			if (shopper == null) {
				throw new ServiceException(500, "您还未登录");
			}
			Set<Object> keys = stringRedisTemplate.opsForHash().keys(ConstantValue.CART + shopper.getId());
			if (CollectionUtils.isEmpty(keys)) {
				return ResponseEntity.success(carts);
			}
			List<Product> products = productMapper.selectByIds(keys);
			Cart cart;
			for (Product product : products) {
				cart = new Cart();
				BeanUtils.copyProperties(product, cart);
				try {
					cart.setCount(Integer.parseInt(String.valueOf(stringRedisTemplate.opsForHash()
							.get(ConstantValue.CART + shopper.getId(), String.valueOf(product.getId())))));
				} catch (Exception e) {
					cart.setCount(1);
				}
				carts.add(cart);
			}

			return ResponseEntity.success(carts);
		} catch (SystemException e) {
			logger.error("mycart", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("cart", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	@RequestMapping(value = "/delCart/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delCart(@PathVariable("id") long productId, HttpServletRequest request) {
		try {
			String loginId = (String) request.getAttribute("loginId");
			Shopper shopper = shopperMapper.selectByPrimaryKey(Long.valueOf(loginId));
			if (shopper == null) {
				throw new ServiceException(500, "您还未登录");
			}
			stringRedisTemplate.opsForHash().delete(ConstantValue.CART + shopper.getId(), String.valueOf(productId));
			return ResponseEntity.success(true);
		} catch (SystemException e) {
			logger.error("mycart", e);
			return ResponseEntity.error(e.getErrorMessage(), e);
		} catch (Exception e) {
			logger.error("cart", e);
			return ResponseEntity.error("未知异常", e);
		}
	}

	/**
	 * 添加订单付款信息
	 * 
	 * @param orderPaymentInfo
	 * @return
	 */
	@RequestMapping(value = "/addPaymentInfo", method = RequestMethod.POST)
	public ResponseEntity<?> paymentInfo(@RequestBody OrderPaymentInfo orderPaymentInfo) {
		try {
			if (orderPaymentInfo.getModelPayment() == null) {
				return ResponseEntity.error("付款方式不能为空", null);
			}
			if (orderPaymentInfo.getPayment() == null) {
				return ResponseEntity.error("付款不能为空", null);
			}
			OrderPaymentInfo lastPatmentInfo = orderPatmentInfoMapper.getLastPatmentInfo(orderPaymentInfo.getOrderId());
			if (lastPatmentInfo == null) {// 表里还没有付款信息所以需要查订单的总金额
				Orders order = orderMapper.selectByPrimaryKey(orderPaymentInfo.getOrderId());
				if (order == null) {
					return ResponseEntity.error("该订单不存在", null);
				}
				orderPaymentInfo.setAccountTotal(order.getOrderPrice());
				orderPaymentInfo.setAccountPaid(orderPaymentInfo.getPayment());

			} else {
				orderPaymentInfo.setAccountTotal(lastPatmentInfo.getAccountTotal());
				orderPaymentInfo.setAccountPaid(lastPatmentInfo.getAccountPaid().add(orderPaymentInfo.getPayment()));
			}
			if (orderPaymentInfo.getAccountPaid().subtract(orderPaymentInfo.getAccountTotal()).doubleValue() > 0) {
				return ResponseEntity.error("已付款不能超过总金额", null);
			}
			orderPatmentInfoMapper.insertPaymentInfo(orderPaymentInfo);
			return ResponseEntity.success(true);

		} catch (Exception e) {
			logger.error("paymentInfo", e);
			return ResponseEntity.error("增加付款信息失败", e);
		}

	}

	/**
	 * 根据订单ID 查询订单付款信息
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/getPaymentInfoByOrderId/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<?> getPayMentInfoByOrderId(@PathVariable long orderId) {
		try {
			if (orderMapper.selectByPrimaryKey(orderId) == null) {
				return ResponseEntity.error("该订单不存在", null);
			}
			return ResponseEntity.success(orderPatmentInfoMapper.getPaymentInfoByOrderId(orderId));
		} catch (Exception e) {
			logger.error("getPayMentInfoByOrderId", e);
			return ResponseEntity.error("查询订单付款信息发生异常", null);
		}

	}

}
