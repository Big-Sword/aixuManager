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
        return this.sqlSessionTemplate.delete("deleteByPrimaryKey",id);
    }

    public int insert(Orders record){
        return this.sqlSessionTemplate.insert("insert",record);
    }

    public int insertSelective(Orders record){
        return this.sqlSessionTemplate.insert("insertSelective",record);
    }

    public Orders selectByPrimaryKey(Long id){
        return this.sqlSessionTemplate.selectOne("com.bao.mapper.OrderMapper.selectByPrimaryKey",id);
    }

    public int updateByPrimaryKeySelective(Orders record){
        return this.sqlSessionTemplate.update("updateByPrimaryKeySelective",record);
    }

    public int updateByPrimaryKey(Orders record){
        return this.sqlSessionTemplate.update("updateByPrimaryKey",record);
    }
}