package com.bao.mapper;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bao.model.Manager;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ManagerMapper {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public long login(Manager manager) {
		Manager managerResult = this.sqlSessionTemplate.selectOne("selectBySelective", manager);
		if(managerResult != null) {
			return managerResult.getId();
		}
		return 0L;
	}

}
