package com.bao.mapper;

import java.util.List;
import java.util.Map;

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
    return this.sqlSessionTemplate.update("deleteById", id) == 0 ? false : true;
  }

  public Product selectById(long id) {
    return this.sqlSessionTemplate.selectOne("com.bao.model.Product.selectById", id);
  }

  public int countAllProducts(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectOne("countTotalProduct", dataTableReqInfo);
  }

  public List<Map<String, Object>> selectAll(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectList("selectAll", dataTableReqInfo);
  }

}
