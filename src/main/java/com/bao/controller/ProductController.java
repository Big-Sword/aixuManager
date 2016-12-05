package com.bao.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.controller.msg.AllProductResponse;
import com.bao.controller.msg.PageRequest;
import com.bao.framework.ResponseEntity;
import com.bao.mapper.ProductMapper;
import com.bao.model.Product;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductMapper mapper;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getInfo(HttpServletRequest request) {
		try {
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			PageRequest pageRequest = new PageRequest();
			pageRequest.setPageIndex(pageIndex * pageSize);
			pageRequest.setPageSize(pageSize);
			AllProductResponse response = new AllProductResponse();
			response.setProductInfos(mapper.selectAll(pageRequest));
			response.setTotal(mapper.countAllProducts());
			return ResponseEntity.success(response);
		} catch (Exception e) {
			logger.error("error to get all product", e);
			return ResponseEntity.error("查询失败", e);
		}
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestBody Product product) {
		try {
			return ResponseEntity.success(mapper.saveOrUpdate(product));
		} catch (Exception e) {
			logger.error("error to saveOrUpdate product", e);
			return ResponseEntity.error("保存或更新失败", e);
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
		try {
			return ResponseEntity.success(mapper.deleteById(Long.parseLong(id)));
		} catch (Exception e) {
			logger.error("error to delete product", e);
			return ResponseEntity.error("删除产品失败", e);
		}
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getInfoById(@PathVariable("id") String id) {
		return ResponseEntity.success(mapper.selectById(Long.parseLong(id)));
	}

}
