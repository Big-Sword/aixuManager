package com.bao.framework.exception;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	protected int errorCode;
	protected String errorMessage;

   public ServiceException(int errorCode, String errorMessage) {
    super(errorMessage);
    setErrorMessage(errorMessage);
    setErrorCode(errorCode);
  }

  public ServiceException(IExceptionCode errorCode) {
    super(ExceptionHelper.getMessage(errorCode));
    this.errorCode = ExceptionHelper.getCode(errorCode);
    this.errorMessage = this.getMessage();
  }

  public ServiceException(IExceptionCode errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = ExceptionHelper.getCode(errorCode);
    this.errorMessage = ExceptionHelper.getMessage(errorCode);
  }

  public ServiceException(IExceptionCode errorCode, String message) {
    super(message);
    this.errorCode = ExceptionHelper.getCode(errorCode);
    this.errorMessage = ExceptionHelper.getMessage(errorCode);
  }

  public ServiceException(IExceptionCode errorCode, Throwable cause) {
    super(ExceptionHelper.getMessage(errorCode), cause);
    this.errorCode = ExceptionHelper.getCode(errorCode);
    this.errorMessage = this.getMessage();
  }

  public ServiceException(String msg) {
    super(msg);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
