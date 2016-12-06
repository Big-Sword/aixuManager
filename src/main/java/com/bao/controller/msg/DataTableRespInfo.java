package com.bao.controller.msg;

import java.util.List;
import java.util.Map;

/**
 * jquery datatable 返回参数
 * 
 * @author sl
 *
 */
public class DataTableRespInfo {
  private int iTotalRecords;// / 数据总数
  private int iTotalDisplayRecords;// 数组显示总数
  private int sEcho; // DataTable请求服务器端次数
  private List<Map<String, Object>> aaData;// 列数据

  public int getiTotalRecords() {
    return iTotalRecords;
  }

  public void setiTotalRecords(int iTotalRecords) {
    this.iTotalRecords = iTotalRecords;
  }

  public int getiTotalDisplayRecords() {
    return iTotalDisplayRecords;
  }

  public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
    this.iTotalDisplayRecords = iTotalDisplayRecords;
  }

  public int getsEcho() {
    return sEcho;
  }

  public void setsEcho(int sEcho) {
    this.sEcho = sEcho;
  }

  public List<Map<String, Object>> getAaData() {
    return aaData;
  }

  public void setAaData(List<Map<String, Object>> aaData) {
    this.aaData = aaData;
  }

}
