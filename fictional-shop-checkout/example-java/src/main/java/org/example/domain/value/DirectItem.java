package org.example.domain.value;

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

  public DirectItem(String typeId, int price) {
    this.typeId = typeId;
    this.price = price;
  }
  public DirectItem(String typeIdAndPrice) {
    this.typeId = typeIdAndPrice.split(":")[0];
    this.price = Integer.parseInt(typeIdAndPrice.split(":")[1]);
  }

  public int getPrice() {
    return price;
  }
}
