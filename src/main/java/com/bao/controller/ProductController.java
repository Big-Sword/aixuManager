package com.bao.controller;

import java.util.List;

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
	public List<Product> getInfo() {
		List<Product> products = mapper.selectAll();
		return products;
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public boolean saveOrUpdate(@RequestBody Product product) {
		return mapper.saveOrUpdate(product);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public boolean deleteProduct(@PathVariable("id") String id) {
		return mapper.deleteById(Long.parseLong(id));
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public Product getInfoById(@PathVariable("id") String id) {
		return mapper.selectById(Long.parseLong(id));
	}

}
