package com.bao.mapper;


import com.bao.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMapper {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int deleteByPrimaryKey(Long id){
        return this.sqlSessionTemplate.delete("com.bao.mapper.OrderMapper.deleteByPrimaryKey",id);
    }

    public long insert(Orders record){
        return this.sqlSessionTemplate.insert("com.bao.mapper.OrderMapper.insert",record);
    }

    public long insertSelective(Orders record){
        return this.sqlSessionTemplate.insert("com.bao.mapper.OrderMapper.insertSelective",record);
    }

    public Orders selectByPrimaryKey(Long id){
        return this.sqlSessionTemplate.selectOne("com.bao.mapper.OrderMapper.selectByPrimaryKey",id);
    }

    public int updateByPrimaryKeySelective(Orders record){
        return this.sqlSessionTemplate.update("com.bao.mapper.OrderMapper.updateByPrimaryKeySelective",record);
    }

    public int updateByPrimaryKey(Orders record){
        return this.sqlSessionTemplate.update("com.bao.mapper.OrderMapper.updateByPrimaryKey",record);
    }
}