package com.bao.controller.msg.orderdata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/6/28.
 */
public class ShopperProductResponse {
	private String name = "";
	private List<ProductResponse> productResponses = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductResponse> getProductResponses() {
		return productResponses;
	}

	public void setProductResponses(List<ProductResponse> productResponses) {
		this.productResponses = productResponses;
	}

	@Override
	public String toString() {
		return "ShopperProductResponse{" +
				"name='" + name + '\'' +
				", productResponses=" + productResponses +
				'}';
	}
}
