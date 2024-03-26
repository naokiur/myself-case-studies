package org.example.controller;

import org.example.application.CheckoutService;

/**
 * 支払いコントローラクラス
 * 受領したパラメータを元にサービスクラスに処理を渡す責務を担う
 */
public class CheckoutController {
  private final CheckoutService checkoutService;

  public CheckoutController(CheckoutService checkoutService) {
    this.checkoutService = checkoutService;
  }

  /**
   * パラメータを受け取り、そのパラメータに応じたお釣りを返却する
   * @param firstRawParam 1つ目のパラメータ
   * @param secondRawParam 2つ目のパラメータ
   * @param thirdRawParam 3つ目のパラメータ
   * @return お釣り
   */
  public ReturnChangeResult returnChange(String firstRawParam, String secondRawParam, String thirdRawParam) {

    var params = new ReturnChangeParam(firstRawParam, secondRawParam, thirdRawParam);
    var result = this.checkoutService.returnChange(params.codes, params.directItems, params.money);

    // HACK: ControllerとService間の依存の低下に繋がればと考え、別クラスに組み替える
    // Controller側でResultをJSON形式にするなどが必要となった場合は、ReturnChangeResultクラスの責務で吸収する
    return new ReturnChangeResult(result.price(), result.message());
  }
}
