package com.bao.controller.msg;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
	

	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
