package com.bao.controller.msg;

import java.util.ArrayList;
import java.util.List;

import com.bao.model.Shopper;

public class AllShopperResponse {

	private List<Shopper> shopperInfos = new ArrayList<>();
	
	private long total;

	public List<Shopper> getShopperInfos() {
		return shopperInfos;
	}

	public void setShopperInfos(List<Shopper> shopperInfos) {
		this.shopperInfos = shopperInfos;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	
}
