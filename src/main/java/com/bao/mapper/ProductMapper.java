package com.bao.mapper;

/**
 * @author gupei
 * @date 2016/3/25.
 * @Description Get transform template by hospital Id from DB
 *
 */

import com.bao.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductMapper {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<Product> selectAll() {
        return this.sqlSessionTemplate.selectList("selectAll");
    }

}
