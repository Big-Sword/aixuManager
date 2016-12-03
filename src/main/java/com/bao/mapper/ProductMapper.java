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

	public boolean saveOrUpdate(Product product) {
		int status;
		if (product.getId() == 0L) {
			status = this.sqlSessionTemplate.insert("createProduct", product);
		} else {
			status = this.sqlSessionTemplate.update("updateById", product);
		}
		return status == 0 ? false : true;
	}

	public boolean deleteById(long id) {
		return this.sqlSessionTemplate.update("deleteById", id) == 0 ? false : true;
	}

	public Product selectById(long id) {
		return this.sqlSessionTemplate.selectOne("selectById", id);
	}

}
