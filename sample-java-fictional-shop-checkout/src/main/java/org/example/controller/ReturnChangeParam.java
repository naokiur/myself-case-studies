package org.example.controller;

import java.util.List;
import java.util.stream.Stream;

/**
 * お釣り処理のためのパラメータクラス
 */
class ReturnChangeParam {

  List<String> codes;
  String money;

  public ReturnChangeParam(String firstParam, String secondParam) {
    // ブランクができた場合は除去する
    this.codes = Stream.of(firstParam.split(",")).filter(v -> !v.isEmpty()).toList();
    this.money = secondParam;
  }
}
