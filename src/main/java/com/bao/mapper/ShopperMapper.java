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
    shopper.setLoginPassword(password);
    if (this.sqlSessionTemplate.insert("createShopper", shopper) > 0) {
      response.setUserName(shopper.getLoginName());
      response.setPassword(password);
      return response;
    }
    return null;
  }

  public List<Map<String, Object>> getAllInfo(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectList("selectAllShopper", dataTableReqInfo);
  }

  public int countAllShopper(DataTableReqInfo dataTableReqInfo) {
    return this.sqlSessionTemplate.selectOne("com.bao.model.Shopper.countTotalShopper",
        dataTableReqInfo);
  }

  public Shopper loginShopper(Shopper shopper) throws Exception {

    return this.sqlSessionTemplate.selectOne("com.bao.model.Shopper.loginShopper", shopper);
  }

  public int updatePassword(Shopper shopper) throws Exception {
    return this.sqlSessionTemplate.update("com.bao.model.Shopper.updateShopperPassword", shopper);
  }

  public Shopper selectByPrimaryKey(long userId) {

    return this.sqlSessionTemplate.selectOne("com.bao.model.Shopper.selectByPrimaryKey", userId);
  }

  public boolean deleteById(long id) throws Exception {
    return this.sqlSessionTemplate.update("com.bao.model.Shopper.deleteById", id) == 0
        ? false
        : true;
  }

  public boolean updateShopper(Shopper shopper) throws Exception {
    return this.sqlSessionTemplate.update("com.bao.model.Shopper.updateShopper", shopper) == 0
        ? false
        : true;
  }

  public long count() {
    return this.sqlSessionTemplate.selectOne("com.bao.model.Shopper.count");
  }

}
