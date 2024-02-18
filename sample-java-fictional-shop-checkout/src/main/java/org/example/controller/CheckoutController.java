package org.example.controller;

import org.example.application.CheckoutService;

public class CheckoutController {
  private final CheckoutService checkoutService;

  public CheckoutController(CheckoutService checkoutService) {
    this.checkoutService = checkoutService;
  }

  public int returnChange(String firstRawParam, String secondRawParam) {

    var params = new ReturnChangeParam(firstRawParam, secondRawParam);

    return this.checkoutService.returnChange(params.codes, params.money);
  }
}
