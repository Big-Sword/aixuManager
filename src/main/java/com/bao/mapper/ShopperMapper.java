package com.bao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bao.controller.msg.DataTableReqInfo;
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
  public ShopperResponse createShopper(Shopper shopper) throws Exception {
    ShopperResponse response = new ShopperResponse();
    String createLoginName = CommonUtils.getRandomLoginName(shopper.getName());
    String password = CommonUtils.getRandomLoginPassword();
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
    shopper.setLoginPassword(DigestUtils.md2Hex(password));
    if (this.sqlSessionTemplate.insert("createShopper", shopper) > 0) {
      response.setUserName(shopper.getLoginName());
      response.setPassword(password);
      return response;
    }
    return null;
  }


  public long countAllShopper() {
    return this.sqlSessionTemplate.selectOne("countTotalShopper");
  }

  public List<Map<String, Object>> getAllInfo(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectList("selectAllShopper", dataTableReqInfo);
  }


  public int countAllShopper(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectOne("countTotalShopper", dataTableReqInfo);
  }
}
