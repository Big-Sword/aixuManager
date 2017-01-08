package com.bao.mapper;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bao.model.OrderPaymentInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderPaymentInfoMapper {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<OrderPaymentInfo> getPaymentInfoByOrderId(long orderId) {
		return this.sqlSessionTemplate.selectList("com.bao.model.OrderPaymentInfo.getPaymentInfoByOrderId",
				orderId);
	}

	public void insertPaymentInfo(OrderPaymentInfo orderPaymentInfo) {
		this.sqlSessionTemplate.insert("com.bao.model.OrderPaymentInfo.insertPaymentInfo", orderPaymentInfo);
	}

}
