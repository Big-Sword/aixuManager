package com.bao.controller.msg;

import java.util.ArrayList;
import java.util.List;

import com.bao.model.Product;

public class AllProductResponse {

	private List<Product> productInfos = new ArrayList<>();

	private long total;

	public List<Product> getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(List<Product> productInfos) {
		this.productInfos = productInfos;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
