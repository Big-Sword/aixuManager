package com.bao.controller.msg.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanruofei on 16/12/6.
 */
public class OrderUpdateRequest {

    private String deliveryTime;//送货时间
    private String weddingTime;//婚礼时间
    private String customer;//收货人
    private String contact;//联系方式
    private String address;//地址
    private List<OrderDetailItem> orderDetailItems = new ArrayList<>();//商品


    public String getDeliveryTime() {
      return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
      this.deliveryTime = deliveryTime;
    }

    public String getWeddingTime() {
      return weddingTime;
    }

    public void setWeddingTime(String weddingTime) {
      this.weddingTime = weddingTime;
    }

    public List<OrderDetailItem> getOrderDetailItems() {
        return orderDetailItems;
    }

    public void setOrderDetailItems(List<OrderDetailItem> orderDetailItems) {
        this.orderDetailItems = orderDetailItems;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderingRequest{" +
                "deliveryTime=" + deliveryTime +
                ", weddingTime=" + weddingTime +
                ", customer='" + customer + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", orderDetailItems=" + orderDetailItems +
                '}';
    }
}
