package com.bao.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bao.controller.msg.DataTableReqInfo;

/**
 * @author gupei
 * @date 2016/3/25.
 * @Description Get transform template by hospital Id from DB
 *
 */

import com.bao.model.Product;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductMapper {


  @Autowired
  private SqlSessionTemplate sqlSessionTemplate;

  public boolean saveOrUpdate(Product product) throws Exception {
    int status;
    if (product.getId() == 0L) {
      status = this.sqlSessionTemplate.insert("com.bao.model.Product.createProduct", product);
    } else {
      status = this.sqlSessionTemplate.update("com.bao.model.Product.updateById", product);
    }
    return status == 0 ? false : true;
  }

  public boolean deleteById(long id) throws Exception {
    return this.sqlSessionTemplate.update("com.bao.model.Product.deleteById", id) == 0
        ? false
        : true;
  }

  public Product selectById(long id) {
    return this.sqlSessionTemplate.selectOne("com.bao.model.Product.selectById", id);
  }

  public int countAllProducts(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectOne("com.bao.model.Product.countTotalProduct",
        dataTableReqInfo);
  }

  public List<Map<String, Object>> selectAll(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectList("com.bao.model.Product.selectAll", dataTableReqInfo);
  }

  public long count() {
    return this.sqlSessionTemplate.selectOne("com.bao.model.Product.count");
  }

  public List<Product> findAll() {
    return this.sqlSessionTemplate.selectList("com.bao.model.Product.findAll");
  }

  public List<Product> selectByIds(Set<Object> keys) {
    List<Long> ids = new ArrayList<>();
    keys.forEach(k -> ids.add(Long.parseLong(String.valueOf(k))));
    Map<String, List<Long>> map = new HashMap<>();
    map.put("ids", ids);
    return this.sqlSessionTemplate.selectList("com.bao.model.Product.selectByIds", map);
  }

}
