package org.example.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class DealTest {

  @Test
  void charge_識別番号の結果350円の商品に対して支払いを400円渡しお釣りが50円であること() {
    var targetCodes = Arrays.asList("001", "002");
    var targetMoney = "400";
    var deal = new Deal(targetCodes, targetMoney);

    assertEquals(50, deal.charge());
  }
}