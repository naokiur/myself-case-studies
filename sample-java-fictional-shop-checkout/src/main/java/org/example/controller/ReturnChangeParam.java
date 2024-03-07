package org.example.controller;

import java.util.List;
import java.util.stream.Stream;

/**
 * お釣り処理のためのパラメータクラス
 */
class ReturnChangeParam {

  List<String> codes;
  List<String> directItems;
  String money;

  private static final String PARAM_ROW_SEPARATE = ",";
  private static final String PARAM_KEY_VALUE_SEPARATE = ":";

  public ReturnChangeParam(String firstParam, String secondParam, String thirdParam) {
    // ブランクができた場合は除去する
    this.codes = Stream.of(firstParam.split(PARAM_ROW_SEPARATE)).filter(v -> !v.isEmpty()).toList();

    // ブランクができた場合、または":"が含まれない場合は除去する
    this.directItems = Stream.of(secondParam.split(PARAM_ROW_SEPARATE))
        .filter(v -> !v.isEmpty())
        .filter(v -> v.contains(PARAM_KEY_VALUE_SEPARATE)).toList();

    this.money = thirdParam;
  }
}
