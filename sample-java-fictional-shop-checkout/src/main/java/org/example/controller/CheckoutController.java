package org.example.controller;

import org.example.application.CheckoutService;

public class CheckoutController {

  public int returnChange(String firstRawParam, String secondRawParam) {

    var params = new ReturnChangeParam(firstRawParam, secondRawParam);

    var service = new CheckoutService();

    return service.returnChange(params.codes, params.money);
  }
}
