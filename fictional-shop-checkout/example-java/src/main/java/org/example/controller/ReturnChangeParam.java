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

  // HACK: idea) 条件分岐が肥大化してきたため、ReturnChangeParamのフィールドもクラス化する
  public ReturnChangeParam(String firstParam, String secondParam, String thirdParam) {
    // ブランクができた場合は除去する
    // 100文字パラメータチェックのために2種類のリストに分割する
    // 100文字を超える場合、key = true. falseが許容できるリスト
    var codesMap = Stream.of(firstParam.split(PARAM_ROW_SEPARATE)).filter(v -> !v.isEmpty()).collect(
        Collectors.partitioningBy(
            v -> v.length() > 100
        ));
    if (!codesMap.get(true).isEmpty()) {
      throw new ParametersException("1行目のパラメータ（識別番号）に100文字を超えた文字列が含まれているため、扱うことができません。");
    }

    if (codesMap.get(false).size() > 50) {
      throw new ParametersException("1行目のパラメータ（識別番号）の個数が50個を超えているため、扱うことができません。");
    }

    // ブランクができた場合、または":"が含まれない場合は除去する
    // 100文字パラメータチェックのために2種類のリストに分割する
    // 100文字を超える場合、key = true. falseが許容できるリスト
    var directItemsMap = Stream.of(secondParam.split(PARAM_ROW_SEPARATE))
        .filter(v -> !v.isEmpty())
        .filter(v -> v.contains(PARAM_KEY_VALUE_SEPARATE)).collect(
            Collectors.partitioningBy(
                // xxx:xxxという形式のため、0文字から:までを種別番号として扱い、その文字数をチェックする
                v -> v.substring(0, v.indexOf(PARAM_KEY_VALUE_SEPARATE)).length() > 100
            )
        );

    if (!directItemsMap.get(true).isEmpty()) {
      throw new ParametersException("2行目のパラメータ（種別番号:値段）の1つ目（種別番号）に100文字を超えた文字列が含まれてるため、扱うことができません。");
    }

    if (directItemsMap.get(false).size() > 50) {
      throw new ParametersException("2行目のパラメータ（種別番号:値段）の個数が50個を超えているため、扱うことができません。");
    }

    this.codes = codesMap.get(false);
    this.directItems = directItemsMap.get(false);
    this.money = thirdParam;
  }
}
