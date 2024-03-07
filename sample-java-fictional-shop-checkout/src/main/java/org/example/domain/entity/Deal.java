package org.example.domain.entity;

import java.util.List;
import org.example.domain.value.DirectItem;
import org.example.domain.value.Item;
import org.example.domain.value.Money;

/**
 * 取引クラス
 * 1取引（1人の顧客による、商品をレジ店員へ渡しお釣りを受け取るまでを表す）は、商品情報と支払い情報を保持する。
 */
public class Deal {

  private final List<Item> items;
  private final List<DirectItem> directItems;
  private final Money money;

  public Deal(List<Item> codes, List<DirectItem> directItems, Money money) {
    this.items = codes;
    this.directItems = directItems;
    this.money = money;
  }

  /**
   * お釣り
   * @return お釣り
   */
  public int charge() {
    // 識別番号の商品の合計金額
    var sumOfItems = this.items.stream().mapToInt(Item::getPrice).sum();
    // 直打ちの商品の合計金額
    var sumOfDirectItems = this.directItems.stream().mapToInt(DirectItem::getPrice).sum();

    // 商品全体の合計金額
    var sumOfDeal = sumOfItems + sumOfDirectItems;

    return this.money.getValue() - sumOfDeal;
  }
}
