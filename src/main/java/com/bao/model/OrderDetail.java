package com.bao.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by asus on 2016/6/28.
 */
@Data
public class OrderDetail {
	private Long id;
	private Long orderId;//订单id
	private Long productId;//商品id
	private Integer productCount;//商品数量

	private String name;// 名称
	private String picUrl;// 图片
	private String content;// 内容
	private BigDecimal price;// 价格
	private String modelType;// 型号
	private String colourType;// 色号

	private int isDelete;
	private Timestamp updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}


	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getColourType() {
		return colourType;
	}

	public void setColourType(String colourType) {
		this.colourType = colourType;
	}

	@Override
	public String toString() {
		return "OrderDetail{" +
				"id=" + id +
				", orderId=" + orderId +
				", productId=" + productId +
				", productCount=" + productCount +
				", name='" + name + '\'' +
				", picUrl='" + picUrl + '\'' +
				", content='" + content + '\'' +
				", price=" + price +
				", modelType='" + modelType + '\'' +
				", colourType='" + colourType + '\'' +
				", isDelete=" + isDelete +
				", updatedAt=" + updatedAt +
				'}';
	}
}
