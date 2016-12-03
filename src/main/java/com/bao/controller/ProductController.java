package com.bao.controller;

import com.bao.mapper.ProductMapper;
import com.bao.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by asus on 2016/6/28.
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductMapper mapper;

    @RequestMapping(value = "/all" , method = RequestMethod.GET)
    public List<Product> getInfo(){
        List<Product> products = mapper.selectAll();
        return products;
    }

}
