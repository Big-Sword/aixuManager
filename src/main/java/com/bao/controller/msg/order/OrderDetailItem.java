package com.bao.controller.msg.order;

/**
 * Created by hanruofei on 16/12/6.
 */
public class OrderDetailItem {

    private long id;//id
    private long productId;//产品id
    private int productNum;//产品数量

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    @Override
    public String toString() {
        return "OrderDetailItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", productNum=" + productNum +
                '}';
    }
}
