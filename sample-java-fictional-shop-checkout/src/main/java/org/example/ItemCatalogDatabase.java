package org.example;

public enum ItemCatalogDatabase {
  HAM("001", 250),
  ORANGE_JUICE("002", 100);

  public final String id;
  public final int price;

  ItemCatalogDatabase(String id, int price) {
    this.id = id;
    this.price = price;
  }
}
