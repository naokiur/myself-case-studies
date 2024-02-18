package org.example.repository;

import java.util.Arrays;
import org.example.ItemCatalogDatabase;
import org.example.domain.value.Item;

public class ItemRepository {
  public Item getById(String itemId) {
    var result  = Arrays.stream(ItemCatalogDatabase.values()).filter(v -> v.id.equals(itemId)).findFirst();
    if (result.isEmpty()) {
      throw new IllegalArgumentException();
    }
    var item = result.get();
    return new Item(item.id, item.price);
  }
}
