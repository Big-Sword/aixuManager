package com.bao.controller;

import java.util.List;

import com.bao.framework.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bao.mapper.ProductMapper;
import com.bao.model.Product;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductMapper mapper;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getInfo() {
		List<Product> products = mapper.selectAll();
		return ResponseEntity.success(products);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestBody Product product) {
		return ResponseEntity.success(mapper.saveOrUpdate(product));
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
		return ResponseEntity.success(mapper.deleteById(Long.parseLong(id)));
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getInfoById(@PathVariable("id") String id) {
		return ResponseEntity.success(mapper.selectById(Long.parseLong(id)));
	}

}
