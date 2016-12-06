package com.bao.controller.msg.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanruofei on 16/12/6.
 */
public class OrderingRequest {

    private long deliveryTime;//送货时间
    private long weddingTime;//婚礼时间
    private List<OrderDetailItem> orderDetailItems = new ArrayList<>();//商品

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getWeddingTime() {
        return weddingTime;
    }

    public void setWeddingTime(long weddingTime) {
        this.weddingTime = weddingTime;
    }

    public List<OrderDetailItem> getOrderDetailItems() {
        return orderDetailItems;
    }

    public void setOrderDetailItems(List<OrderDetailItem> orderDetailItems) {
        this.orderDetailItems = orderDetailItems;
    }

    @Override
    public String toString() {
        return "OrderingRequest{" +
                "deliveryTime=" + deliveryTime +
                ", weddingTime=" + weddingTime +
                ", orderDetailItems=" + orderDetailItems +
                '}';
    }
}
