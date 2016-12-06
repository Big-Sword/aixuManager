package com.bao.mapper;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bao.controller.msg.PageRequest;
import com.bao.controller.msg.ShopperResponse;
import com.bao.model.Shopper;
import com.bao.utils.CommonUtils;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShopperMapper {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Transactional
	public ShopperResponse createShopper(Shopper shopper) throws Exception {
		ShopperResponse response = new ShopperResponse();
		String contactWay = shopper.getContactWay();
		String password = CommonUtils.getRandomLoginPassword();
		String loginName = this.sqlSessionTemplate.selectOne("selectByContactWay", contactWay);
		if (!StringUtils.isNullOrEmpty(loginName)) {
			if (loginName.equals(contactWay)) {
				contactWay += "_1";
			} else {
				int version = Integer.parseInt(loginName.substring(loginName.lastIndexOf("_") + 1));
				version++;
				contactWay += "_" + version;
			}
		}
		shopper.setLoginName(contactWay);
		shopper.setLoginPassword(DigestUtils.md2Hex(password));
		if (this.sqlSessionTemplate.insert("createShopper", shopper) > 0) {
			response.setUserName(shopper.getLoginName());
			response.setPassword(password);
			return response;
		}
		return null;
	}

	public List<Shopper> getAllInfo(PageRequest pageRequest) {
		return this.sqlSessionTemplate.selectList("selectAllShopper", pageRequest);
	}

	public long countAllShopper() {
		return this.sqlSessionTemplate.selectOne("countTotalShopper");
	}

	@Transactional
	public Shopper loginShopper(Shopper shopper) throws Exception {

		return this.sqlSessionTemplate.selectOne("loginShopper", shopper);
	}

	public int updatePassword(Shopper shopper) throws Exception {
		return this.sqlSessionTemplate.update("updateShopperPassword", shopper);
	}

}
