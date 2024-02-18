package org.example.application;

import java.util.List;
import org.example.domain.entity.Deal;

public class CheckoutService {

  public int returnChange(List<String> codes, String money) {
    var deal = new Deal(codes, money);
    return deal.charge();
  }
}
