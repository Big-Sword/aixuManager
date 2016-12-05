package com.bao.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by asus on 2016/6/28.
 */
@Data
public class OrderDetail {
	private long id;
	private long orderId;//订单id
	private long shopperId;//商品id
	private int shopperCount;//商品数量

	private int isDelete;
	private Timestamp createdAt;
	private Timestamp updatedAt;

}
