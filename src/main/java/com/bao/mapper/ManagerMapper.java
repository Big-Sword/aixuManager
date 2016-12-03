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

	public boolean login(Manager manager) {
		if (this.sqlSessionTemplate.selectOne("selectBySelective", manager) != null) {
			return true;
		}
		return false;
	}

}
