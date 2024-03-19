package org.example.domain.value;

import org.example.domain.DomainException;

/**
 * 商品情報（直打ち）
 */
public class DirectItem {

  /**
   * 商品の金額
   */
  private final int price;

  /**
   * 商品の種別番号
   */
  private final String typeId;

  private static final int PRICE_MAX_VALUE = 1000000;

  public DirectItem(String typeId, int price) {
    this.typeId = typeId;
    this.price = price;
  }
  public DirectItem(String typeIdAndPrice) {
    var values = typeIdAndPrice.split(":");
    var rawTypeId = values[0];
    var rawPrice = values[1];

    this.typeId = rawTypeId;
    this.price = Integer.parseInt(rawPrice);

    if (this.price > PRICE_MAX_VALUE) {
      throw new DomainException("直打ち商品：金額が1,000,000円を超えているため、扱うことができません。");
    }

  }

  public int getPrice() {
    return price;
  }
}
