package com.bao.mapper;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bao.controller.msg.ShopperResponse;
import com.bao.model.Shopper;
import com.bao.utils.CommonUtils;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShopperMapper {
	private static final String LOGO = "@AIXU";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Transactional
	public ShopperResponse createShopper(Shopper shopper) {
		ShopperResponse response = new ShopperResponse();
		String createLoginName = CommonUtils.getRandomLoginName(shopper.getName());
		String loginName = this.sqlSessionTemplate.selectOne("selectByName", createLoginName);
		if (StringUtils.isNullOrEmpty(loginName)) {
			createLoginName += "_1";
		} else {
			int version = Integer
					.parseInt(loginName.substring(loginName.lastIndexOf("_") + 1, loginName.indexOf(LOGO)));
			version++;
			createLoginName += "_" + version;
		}
		shopper.setLoginName(createLoginName + LOGO);
		shopper.setLoginPassword(DigestUtils.md2Hex(CommonUtils.getRandomLoginPassword()));
		if (this.sqlSessionTemplate.insert("createShopper", shopper) > 0) {
			response.setUserName(shopper.getLoginName());
			response.setPassword(shopper.getLoginPassword());
			return response;
		}
		return null;
	}

	public List<Shopper> getAllInfo() {
		return this.sqlSessionTemplate.selectList("selectAllShopper");
	}
}
