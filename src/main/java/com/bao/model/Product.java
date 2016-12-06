package com.bao.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by asus on 2016/6/28.
 */
@Data
public class Product {
	private long id;
	private String name = "";// 名称
	private String picUrl = "";// 图片
	private String content = "";// 内容
	private Double price;// 价格
	private int stock;// 库存
	private String modelType = "";// 型号
	private String colourType = "";// 色号
	private String remark = "";// 备注
	private int isDelete;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Product{" + "id=" + id + ", name='" + name + '\'' + ", picUrl='" + picUrl + '\'' + ", content='"
				+ content + '\'' + ", price=" + price + ", stock=" + stock + ", modelType='" + modelType + '\''
				+ ", colourType='" + colourType + '\'' + ", remark='" + remark + '\'' + ", isDelete=" + isDelete
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
	}
}
