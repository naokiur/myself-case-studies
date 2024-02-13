package org.example.controller;

import java.util.List;

class ReturnChangeParam {

  List<String> codes;
  String money;

  public ReturnChangeParam(String firstParam, String secondParam) {
    this.codes = List.of(firstParam.split(","));
    this.money = secondParam;
  }
}
