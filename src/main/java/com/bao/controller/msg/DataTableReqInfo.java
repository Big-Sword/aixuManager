package com.bao.controller.msg;

/**
 * jquery datatable 请求参数
 * 
 * @author sl
 *
 */
public class DataTableReqInfo {
  private int iDisplayStart;// / 分页时每页跨度数量
  private int iDisplayLength;// 每页显示的数量
  private int sEcho; // DataTable请求服务器端次数
  private String sSearch;// 前台search条件

  public int getiDisplayStart() {
    return iDisplayStart;
  }

  public DataTableReqInfo() {}

  public DataTableReqInfo(int iDisplayStart, int iDisplayLength, int sEcho, String sSearch) {
    this.iDisplayStart = iDisplayStart;
    this.iDisplayLength = iDisplayLength;
    this.sEcho = sEcho;
    this.sSearch = sSearch;
  }

  public void setiDisplayStart(int iDisplayStart) {
    this.iDisplayStart = iDisplayStart;
  }

  public int getiDisplayLength() {
    return iDisplayLength;
  }

  public void setiDisplayLength(int iDisplayLength) {
    this.iDisplayLength = iDisplayLength;
  }

  public int getsEcho() {
    return sEcho;
  }

  public void setsEcho(int sEcho) {
    this.sEcho = sEcho;
  }

  public String getsSearch() {
    return sSearch;
  }

  public void setsSearch(String sSearch) {
    this.sSearch = sSearch;
  }

}
