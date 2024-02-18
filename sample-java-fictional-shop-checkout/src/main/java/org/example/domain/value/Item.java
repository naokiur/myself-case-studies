package org.example.domain.value;

public class Item {
  private final int price;
  private final String id;

  public Item(String id, int price) {
    this.id = id;
    this.price = price;
  }

  public int getPrice() {
    return price;
  }
}
