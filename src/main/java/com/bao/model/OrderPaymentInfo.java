package com.bao.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderPaymentInfo {

	private long id;

	private long orderId;

	private BigDecimal payment;

	private BigDecimal accountPaid;

	private BigDecimal accountTotal;

	private Integer modelPayment;

	private String remark = "";

	private Timestamp createdAt;

	private Timestamp updatedAt;

	private int isDelete;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getModelPayment() {
		return modelPayment;
	}

	public void setModelPayment(Integer modelPayment) {
		this.modelPayment = modelPayment;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getAccountPaid() {
		return accountPaid;
	}

	public void setAccountPaid(BigDecimal accountPaid) {
		this.accountPaid = accountPaid;
	}

	public BigDecimal getAccountTotal() {
		return accountTotal;
	}

	public void setAccountTotal(BigDecimal accountTotal) {
		this.accountTotal = accountTotal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
