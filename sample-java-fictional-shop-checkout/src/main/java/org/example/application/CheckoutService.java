package org.example.application;

import java.util.List;
import org.example.domain.entity.Deal;

public class CheckoutService {

  public int returnChange(List<String> codes, String params) {
    var deal = new Deal();
    System.out.println("Hello world!" + params);
    System.out.println("Hello world!" + String.join(",", codes));

    return deal.charge();
  }
}
