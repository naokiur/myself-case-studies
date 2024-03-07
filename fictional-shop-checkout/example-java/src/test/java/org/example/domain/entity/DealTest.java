package org.example.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.example.domain.value.DirectItem;
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
    var deal = new Deal(targetItems, List.of(), targetMoney);

    assertEquals(50, deal.charge());
  }

  @Test
  void charge_識別番号001_種別番号900_300円の結果550円の商品に対して支払いを1000円渡しお釣りが450円であること() {
    var targetItems = List.of(
        new Item("001", 250)
    );
    var targetDirectItems = List.of(
        new DirectItem("900", 300)
    );
    var targetMoney = new Money("1000");
    var deal = new Deal(targetItems, targetDirectItems, targetMoney);

    assertEquals(450, deal.charge());
  }

  @Test
  void charge_識別番号001と002_種別番号900_300円と800_200円の結果850円の商品に対して支払いを1000円渡しお釣りが450円であること() {
    var targetItems = List.of(
        new Item("001", 250),
        new Item("002", 100)
    );
    var targetDirectItems = List.of(
        new DirectItem("900", 300),
        new DirectItem("800", 200)
    );
    var targetMoney = new Money("1000");
    var deal = new Deal(targetItems, targetDirectItems, targetMoney);

    assertEquals(150, deal.charge());
  }
}