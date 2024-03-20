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

  public record DealResult(int price, String message) {}
  private final List<Item> items;
  private final List<DirectItem> directItems;
  private final Money money;

  private static final int DEAL_SIZE_MAX_VALUE = 100;
  private static final int DEAL_PRICE_MAX_VALUE = 1000000;
  private static final int DEAL_NEED_STAMP_VALUE = 30000;
  private static final String MESSAGE_FORMAT_LACK_MONEY = "取引情報：商品の合計金額よりも支払い情報が%d円不足しています。お客様に確認してください。";

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
   * @return お釣り情報
   */
  public DealResult charge() {
    // 識別番号の商品の合計金額
    var sumOfItems = this.items.stream().mapToInt(Item::getPrice).sum();
    // 直打ちの商品の合計金額
    var sumOfDirectItems = this.directItems.stream().mapToInt(DirectItem::getPrice).sum();

    // 商品全体の合計金額
    var sumOfDeal = sumOfItems + sumOfDirectItems;

    if (sumOfDeal > DEAL_PRICE_MAX_VALUE) {
      throw new DomainException("取引情報：合計金額が1,000,000円を超えているため、扱うことができません。");
    }

    var charge = this.money.getValue() - sumOfDeal;

    if (0 > charge) {
      throw new DomainException(MESSAGE_FORMAT_LACK_MONEY.formatted(Math.abs(charge)));
    }

    // 収入印紙メッセージの追加
    if (sumOfDeal > DEAL_NEED_STAMP_VALUE) {
      return new DealResult(this.money.getValue() - sumOfDeal, "レジ店員の方へ：収入印紙200円を1枚貼付してください。");
    }

    // それ以外は空文字列
    return new DealResult(this.money.getValue() - sumOfDeal, "");
  }
}
