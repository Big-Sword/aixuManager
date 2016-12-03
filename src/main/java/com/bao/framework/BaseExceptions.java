package com.bao.framework;


import com.bao.framework.exception.Code;
import com.bao.framework.exception.Desc;
import com.bao.framework.exception.IExceptionCode;

public enum BaseExceptions implements IExceptionCode {
	@Desc("未知异常")
	@Code(-1)
	UNKNOWN_EXCEPTION,

	
	@Desc("JSON转换出错!")
	@Code(4001)
	JsonTransError,
	
}
