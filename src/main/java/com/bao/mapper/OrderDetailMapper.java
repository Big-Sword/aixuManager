package com.bao.mapper;


import com.bao.model.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
public class OrderDetailMapper {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int deleteByPrimaryKey(Long id){
        return this.sqlSessionTemplate.delete("com.bao.mapper.OrderDetailMapper.deleteByPrimaryKey",id);
    }

    public int insert(OrderDetail record){
        return this.sqlSessionTemplate.insert("com.bao.mapper.OrderDetailMapper.insert",record);
    }

    public int insertSelective(OrderDetail record){
        return this.sqlSessionTemplate.insert("com.bao.mapper.OrderDetailMapper.insertSelective",record);
    }

    public void batchInsert(List<OrderDetail> list){
        this.sqlSessionTemplate.insert("com.bao.mapper.OrderDetailMapper.batchInsert",list);
    }

    public OrderDetail selectByPrimaryKey(Long id){
        return this.sqlSessionTemplate.selectOne("com.bao.mapper.OrderDetailMapper.selectByPrimaryKey",id);
    }

    public int updateByPrimaryKeySelective(OrderDetail record){
        return this.sqlSessionTemplate.update("com.bao.mapper.OrderDetailMapper.updateByPrimaryKeySelective",record);
    }

    public int updateByPrimaryKey(OrderDetail record){
        return this.sqlSessionTemplate.update("com.bao.mapper.OrderDetailMapper.updateByPrimaryKey",record);
    }

    public List<OrderDetail> selectBySelective(OrderDetail record){
        return this.sqlSessionTemplate.selectList("com.bao.mapper.OrderDetailMapper.selectBySelective",record);
    }

    public List<OrderDetail> selectByIds(OrderDetail record){
        return this.sqlSessionTemplate.selectList("com.bao.mapper.OrderDetailMapper.selectBySelective",record);
    }


    public List<OrderDetail> selectByProductIds(Set<Long> keys, Timestamp start, Timestamp end) {
        List<Long> ids = new ArrayList<>();
        keys.forEach(k -> ids.add(k));
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("start", start);
        map.put("end", end);
        return this.sqlSessionTemplate.selectList("com.bao.mapper.OrderDetailMapper.selectByProductIds", map);
    }

    public List<OrderDetail> selectByOrderIds(Set<Long> keys) {
        List<Long> ids = new ArrayList<>();
        keys.forEach(k -> ids.add(k));
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        return this.sqlSessionTemplate.selectList("com.bao.mapper.OrderDetailMapper.selectByOrderIds", map);
    }
}
