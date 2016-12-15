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
  private int iSortCol_0;// 前台需要排序的列
  private String sSortDir_0;// 排序方式
  

  public int getiSortCol_0() {
    return iSortCol_0;
  }

  public void setiSortCol_0(int iSortCol_0) {
    this.iSortCol_0 = iSortCol_0;
  }

  public String getsSortDir_0() {
    return sSortDir_0;
  }
  

  public void setsSortDir_0(String sSortDir_0) {
    this.sSortDir_0 = sSortDir_0;
  }

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
