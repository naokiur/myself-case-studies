package org.example.controller;

import java.util.List;
import java.util.stream.Collectors;
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
    // 100文字パラメータチェックのために2種類のリストに分割する
    var codesMap = Stream.of(firstParam.split(PARAM_ROW_SEPARATE)).filter(v -> !v.isEmpty()).collect(
        Collectors.partitioningBy(
            v -> v.length() <= 100
        ));
    if (!codesMap.get(false).isEmpty()) {
      throw new ParametersException("識別番号が100文字を超えている商品が含まれています。");
    }
    this.codes = codesMap.get(true);

    // ブランクができた場合、または":"が含まれない場合は除去する
    this.directItems = Stream.of(secondParam.split(PARAM_ROW_SEPARATE))
        .filter(v -> !v.isEmpty())
        .filter(v -> v.contains(PARAM_KEY_VALUE_SEPARATE)).toList();

    this.money = thirdParam;
  }
}
