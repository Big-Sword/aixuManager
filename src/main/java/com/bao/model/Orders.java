package com.bao.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by asus on 2016/6/28.
 */
@Data
public class Orders {
	private Long id;
	private String orderNum;//订单号
	private Long shopperId;
	private String shopperName;
	private BigDecimal orderPrice;//订单总额
	private String customer;//收货人
	private String contact;//联系方式
	private String address;//地址
	private Integer status;//订单状态
	private Timestamp deliveryTime;//送货时间
	private Timestamp weddingTime;//婚礼时间
	private Timestamp orderTime;//下单时间
	

	private int isDelete;
	private Timestamp updatedAt;
	
	

	public String getShopperName() {
    return shopperName;
  }

  public void setShopperName(String shopperName) {
    this.shopperName = shopperName;
  }

  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getShopperId() {
		return shopperId;
	}

	public void setShopperId(Long shopperId) {
		this.shopperId = shopperId;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Timestamp getWeddingTime() {
		return weddingTime;
	}

	public void setWeddingTime(Timestamp weddingTime) {
		this.weddingTime = weddingTime;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Orders{" +
				"id=" + id +
				", orderNum='" + orderNum + '\'' +
				", shopperId=" + shopperId +
				", orderPrice=" + orderPrice +
				", customer='" + customer + '\'' +
				", contact='" + contact + '\'' +
				", address='" + address + '\'' +
				", status=" + status +
				", deliveryTime=" + deliveryTime +
				", weddingTime=" + weddingTime +
				", orderTime=" + orderTime +
				", isDelete=" + isDelete +
				", updatedAt=" + updatedAt +
				'}';
	}
}
