package com.bao.controller.msg;

public class IndexResponse {

  private long orderCount;
  private long shopperCount;
  private long productCount;

  public long getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(long orderCount) {
    this.orderCount = orderCount;
  }

  public long getShopperCount() {
    return shopperCount;
  }

  public void setShopperCount(long shopperCount) {
    this.shopperCount = shopperCount;
  }

  public long getProductCount() {
    return productCount;
  }

  public void setProductCount(long productCount) {
    this.productCount = productCount;
  }

}
