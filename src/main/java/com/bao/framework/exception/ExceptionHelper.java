package com.bao.framework.exception;

import java.lang.reflect.Field;

public class ExceptionHelper {
	public static String getMessage(IExceptionCode code) {
    if (code == null) return "UNKNOWN_EXCEPTION";
    try {
      Field field = code.getClass().getField(code.name());
      Desc descAnnotation = field.getAnnotation(Desc.class);
      if (descAnnotation == null)
        return "UNKNOWN_EXCEPTION";
      else if (descAnnotation.value() == null || "".equals(descAnnotation.value().trim()))
        return "UNKNOWN_EXCEPTION";
      else
        return descAnnotation.value();
    } catch (Throwable e) {
      return "UNKNOWN_EXCEPTION";
    }
  }

  public static int getCode(IExceptionCode code) {
    if (code == null) return -1;
    try {
      Field field = code.getClass().getField(code.name());
      Code codeAnnotation = field.getAnnotation(Code.class);
      if (codeAnnotation == null)
        return -1;
      else
        return codeAnnotation.value();
    } catch (Throwable e) {
      return -1;
    }
  }
}
