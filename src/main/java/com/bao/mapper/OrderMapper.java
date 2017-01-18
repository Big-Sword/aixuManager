package com.bao.mapper;


import com.bao.controller.msg.DataTableReqInfo;
import com.bao.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
public class OrderMapper {
  @Autowired
  private SqlSessionTemplate sqlSessionTemplate;

  public int deleteByPrimaryKey(Long id) {
    return this.sqlSessionTemplate.delete("com.bao.mapper.OrderMapper.deleteByPrimaryKey", id);
  }

  public long insert(Orders record) {
    return this.sqlSessionTemplate.insert("com.bao.mapper.OrderMapper.insert", record);
  }

  public long insertSelective(Orders record) {
    return this.sqlSessionTemplate.insert("com.bao.mapper.OrderMapper.insertSelective", record);
  }

  public Orders selectByPrimaryKey(Long id) {
    return this.sqlSessionTemplate.selectOne("com.bao.mapper.OrderMapper.selectByPrimaryKey", id);
  }

  public int updateByPrimaryKeySelective(Orders record) {
    return this.sqlSessionTemplate.update("com.bao.mapper.OrderMapper.updateByPrimaryKeySelective",
        record);
  }

  public int updateByPrimaryKey(Orders record) {
    return this.sqlSessionTemplate.update("com.bao.mapper.OrderMapper.updateByPrimaryKey", record);
  }

  public List<Map<String, Object>> selectBySelective(DataTableReqInfo dataTableReqInfo,
      Orders orders) {
    Map<String, Object> param = new HashMap<>();
    param.put("dataTableReqInfo", dataTableReqInfo);
    param.put("orders", orders);
    return this.sqlSessionTemplate.selectList("com.bao.mapper.OrderMapper.selectBySelective",
        param);
  }

  public int countBySelective(DataTableReqInfo dataTableReqInfo, Orders orders) {
    Map<String, Object> param = new HashMap<>();
    param.put("dataTableReqInfo", dataTableReqInfo);
    param.put("orders", orders);
    return this.sqlSessionTemplate.selectOne("com.bao.mapper.OrderMapper.countBySelective", param);
  }

  public List<Orders> getBySelective(Orders record) {
    return this.sqlSessionTemplate.selectList("com.bao.mapper.OrderMapper.getBySelective", record);
  }

  public long count() {
    return this.sqlSessionTemplate.selectOne("com.bao.mapper.OrderMapper.count");
  }

  public List<Orders> selectByShopperId(Long shopperId, Timestamp start, Timestamp end) {
    Map<String, Object> map = new HashMap<>();
    map.put("shopperId", shopperId);
    map.put("start", start);
    map.put("end", end);
    return this.sqlSessionTemplate.selectList("com.bao.mapper.OrderMapper.selectByShopperId", map);
  }
}
