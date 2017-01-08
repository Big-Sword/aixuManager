package com.bao.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderPaymentInfo {

	private long id;

	private long orderId;

	private BigDecimal advance;

	private BigDecimal arrearage;

	private BigDecimal accountPaid;

	private Integer modelPayment;

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

	public BigDecimal getAdvance() {
		return advance;
	}

	public void setAdvance(BigDecimal advance) {
		this.advance = advance;
	}

	public BigDecimal getArrearage() {
		return arrearage;
	}

	public void setArrearage(BigDecimal arrearage) {
		this.arrearage = arrearage;
	}

	public BigDecimal getAccountPaid() {
		return accountPaid;
	}

	public void setAccountPaid(BigDecimal accountPaid) {
		this.accountPaid = accountPaid;
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

}
