package com.bao.controller.msg.order;

import com.bao.model.OrderDetail;
import com.bao.model.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanruofei on 16/12/6.
 */
public class OrderingResponse {

    private Orders orders = new Orders();
    private List<OrderDetail> orderDetails = new ArrayList<>();//商品

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderingResponse{" +
                "orders=" + orders +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
