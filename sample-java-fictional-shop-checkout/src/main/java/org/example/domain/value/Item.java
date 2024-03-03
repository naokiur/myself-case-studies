package org.example.domain.value;

/**
 * 商品情報
 */
public class Item {

  /**
   * 商品の金額
   */
  private final int price;

  /**
   * 商品の識別番号
   */
  private final String id;

  public Item(String id, int price) {
    this.id = id;
    this.price = price;
  }

  public int getPrice() {
    return price;
  }
}
