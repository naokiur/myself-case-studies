package org.example.domain.entity;

import java.util.List;
import org.example.domain.value.Item;
import org.example.domain.value.Money;

/**
 * 取引クラス
 */
public class Deal {

  private final List<Item> items;
  private final Money money;

  public Deal(List<Item> codes, Money money) {
    this.items = codes;
    this.money = money;
  }

  public int charge() {
    return this.money.getValue() - this.items.stream().mapToInt(Item::getPrice).sum();
  }
}
