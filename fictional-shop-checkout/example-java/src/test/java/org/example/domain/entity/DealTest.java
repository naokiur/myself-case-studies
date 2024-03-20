package org.example.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.example.domain.DomainException;
import org.example.domain.value.DirectItem;
import org.example.domain.value.Item;
import org.example.domain.value.Money;
import org.junit.jupiter.api.Test;

class DealTest {

  @Test
  void 合計商品数が100を超えるとき例外が発生すること() {
    var targetItems = List.of(
        new Item("001", 250),
        new Item("002", 100)
    );
    var targetDirectItems = IntStream.range(0, 100).mapToObj(v -> new DirectItem("00" + v, 250))
        .toList();
    var targetMoney = new Money("1000");

    DomainException e = assertThrows(DomainException.class,
        () -> new Deal(targetItems, targetDirectItems, targetMoney));
    assertEquals("取引情報：合計商品数が100を超えているため、扱うことができません。", e.getMessage());
  }

  @Test
  void charge_識別番号の結果350円の商品に対して支払いを400円渡しお釣りが50円であること() {
    var targetItems = Arrays.asList(
        new Item("001", 250),
        new Item("002", 100)
    );
    var targetMoney = new Money("400");
    var deal = new Deal(targetItems, List.of(), targetMoney);

    assertEquals(50, deal.charge().price());
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

    assertEquals(450, deal.charge().price());
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

    assertEquals(150, deal.charge().price());
  }

  @Test
  void charge_合計金額が1000000を超えるとき例外が発生すること() {
    var targetItems = new ArrayList<Item>();

    var targetDirectItems = IntStream.range(0, 99).mapToObj(
        v -> new DirectItem("00" + v, 10000)
        // HACK: Mutable List取得するためstream.toListを使用しない。
        // 最後だけ値を変えたテストデータを投入するため
    ).collect(Collectors.toList());
    var lastDirectItem = new DirectItem("last", 10001);
    targetDirectItems.add(lastDirectItem);

    var targetMoney = new Money("1000000");
    var deal = new Deal(targetItems, targetDirectItems, targetMoney);

    DomainException e = assertThrows(DomainException.class,
        deal::charge);
    assertEquals("取引情報：合計金額が1,000,000円を超えているため、扱うことができません。",
        e.getMessage());
  }

  @Test
  void charge_合計金額よりも支払い情報が少ないとき例外が発生すること() {
    var targetItems = List.of(
        new Item("001", 250),
        new Item("002", 100)
    );
    var targetDirectItems = List.of(
        new DirectItem("900", 300),
        new DirectItem("800", 200)
    );
    var targetMoney = new Money("500");
    var deal = new Deal(targetItems, targetDirectItems, targetMoney);

    DomainException e = assertThrows(DomainException.class, deal::charge);
    assertEquals(
        "取引情報：商品の合計金額よりも支払い情報が350円不足しています。お客様に確認してください。",
        e.getMessage());

  }
}