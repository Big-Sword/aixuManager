package com.bao.mapper;


import com.bao.model.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderDetailMapper {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int deleteByPrimaryKey(Long id){
        return this.sqlSessionTemplate.delete("deleteByPrimaryKey",id);
    }

    public int insert(OrderDetail record){
        return this.sqlSessionTemplate.insert("insert",record);
    }

    public int insertSelective(OrderDetail record){
        return this.sqlSessionTemplate.insert("insertSelective",record);
    }

    public OrderDetail selectByPrimaryKey(Long id){
        return this.sqlSessionTemplate.selectOne("selectByPrimaryKey",id);
    }

    public int updateByPrimaryKeySelective(OrderDetail record){
        return this.sqlSessionTemplate.update("updateByPrimaryKeySelective",record);
    }

    public int updateByPrimaryKey(OrderDetail record){
        return this.sqlSessionTemplate.update("updateByPrimaryKey",record);
    }
}