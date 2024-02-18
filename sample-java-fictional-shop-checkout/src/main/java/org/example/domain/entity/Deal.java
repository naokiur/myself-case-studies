package org.example.domain.entity;

import java.util.List;
import org.example.domain.value.Item;
import org.example.domain.value.Money;
import org.example.repository.ItemRepository;

/**
 * 取引クラス
 */
public class Deal {

  private final List<Item> items;
  private final Money money;

  public Deal(List<String> codes, String money) {
    var repository = new ItemRepository();
    this.items = codes.stream().map(repository::getById).toList();
    this.money = new Money(money);
  }

  public int charge() {
    return this.money.getValue() - this.items.stream().mapToInt(Item::getPrice).sum();
  }
}
