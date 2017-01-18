package com.bao.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * bean转换工具类
 * 
 * @author sl
 *
 */
public final class BeanSwapUtils {
  private BeanSwapUtils(){}

  public static <O, N> List<N> swapList(List<O> orginList, Class<N> newClass)
       {
    if (orginList==null || orginList.size()==0) {
      return Collections.emptyList();
    }
    List<N> newList = new ArrayList<>();
    for (O o : orginList) {
      N n = null;
      try {
        n = newClass.newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      BeanUtils.copyProperties(o, n);
      newList.add(n);
    }
    return newList;
  }

  public static <N, O> N swapObj(O orginObj, Class<N> newClass) {
    try {
      if (orginObj == null) {
        return newClass.newInstance();
      }
      N n = newClass.newInstance();
      BeanUtils.copyProperties(orginObj, n);
      return n;
    }catch (Exception e){
      return null;
    }
  }
}
