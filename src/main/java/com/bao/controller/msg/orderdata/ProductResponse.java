package com.bao.controller.msg.orderdata;

import java.math.BigDecimal;

/**
 * Created by asus on 2016/6/28.
 */
public class ProductResponse {
	private long id;
	private String name = "";// 名称
	private String picUrl = "";// 图片
	private String content = "";// 内容
	private BigDecimal price;// 价格
	private String modelType = "";// 型号
	private String colourType = "";// 色号
	private int num;//产品数量

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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "ProductResponse{" +
				"id=" + id +
				", name='" + name + '\'' +
				", picUrl='" + picUrl + '\'' +
				", content='" + content + '\'' +
				", price=" + price +
				", modelType='" + modelType + '\'' +
				", colourType='" + colourType + '\'' +
				", num=" + num +
				'}';
	}
}
