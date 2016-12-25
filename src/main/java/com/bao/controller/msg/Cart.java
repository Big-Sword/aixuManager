package com.bao.controller.msg;

import java.math.BigDecimal;

public class Cart {
  private long id;
  private String name = "";// 名称
  private String picUrl = "";// 图片
  private String content = "";// 内容
  private BigDecimal price;// 价格
  private int count;// 库存
  private String modelType = "";// 型号
  private String colourType = "";// 色号

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

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
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
}
