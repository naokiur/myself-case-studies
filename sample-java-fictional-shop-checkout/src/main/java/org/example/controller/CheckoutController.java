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
   * @return お釣り
   */
  public int returnChange(String firstRawParam, String secondRawParam) {

    var params = new ReturnChangeParam(firstRawParam, secondRawParam);

    return this.checkoutService.returnChange(params.codes, params.money);
  }
}
