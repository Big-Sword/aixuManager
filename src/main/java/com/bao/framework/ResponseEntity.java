package com.bao.framework;

public class ResponseEntity<E> {
	private Integer code;
	private String msg;
	private E data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public ResponseEntity(Integer code, String msg, E data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static <E> ResponseEntity<E> success(E data) {
		return new ResponseEntity<E>(200, "success", data);
	}
	public static <E> ResponseEntity<E> errorToken() {
		return new ResponseEntity<E>(401, "error token", null);
	}
	public static <E> ResponseEntity<E> error(String msg, E data) {
		return new ResponseEntity<E>(500, msg, data);
	}
}
