package org.example.domain.entity;

import java.util.List;
import org.example.domain.value.Item;
import org.example.domain.value.Money;

/**
 * 取引クラス
 * 1取引（1人の顧客による、商品をレジ店員へ渡しお釣りを受け取るまでを表す）は、商品情報と支払い情報を保持する。
 */
public class Deal {

  private final List<Item> items;
  private final Money money;

  public Deal(List<Item> codes, Money money) {
    this.items = codes;
    this.money = money;
  }

  /**
   * お釣り
   * @return お釣り
   */
  public int charge() {
    return this.money.getValue() - this.items.stream().mapToInt(Item::getPrice).sum();
  }
}
