package org.example.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.example.domain.value.Item;
import org.example.domain.value.Money;
import org.junit.jupiter.api.Test;

class DealTest {

  @Test
  void charge_識別番号の結果350円の商品に対して支払いを400円渡しお釣りが50円であること() {
    var targetItems = Arrays.asList(
        new Item("001", 250),
        new Item("002", 100)
    );
    var targetMoney = new Money("400");
    var deal = new Deal(targetItems, targetMoney);

    assertEquals(50, deal.charge());
  }
}