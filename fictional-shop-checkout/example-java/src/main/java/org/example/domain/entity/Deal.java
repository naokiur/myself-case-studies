package org.example.domain.entity;

import java.util.List;
import org.example.domain.DomainException;
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

  private static final int DEAL_SIZE_MAX_VALUE = 100;
  private static final int DEAL_PRICE_MAX_VALUE = 1000000;

  public Deal(List<Item> codes, List<DirectItem> directItems, Money money) {

    if (codes.size() + directItems.size() > DEAL_SIZE_MAX_VALUE) {
      throw new DomainException("取引情報：合計商品数が100を超えているため、扱うことができません。");
    }

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

    if (sumOfDeal > DEAL_PRICE_MAX_VALUE) {
      throw new DomainException("取引情報：合計金額が1,000,000円を超えているため、扱うことができません。");
    }

    return this.money.getValue() - sumOfDeal;
  }
}
