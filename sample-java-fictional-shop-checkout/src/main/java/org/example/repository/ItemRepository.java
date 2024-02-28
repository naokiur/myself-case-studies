package org.example.repository;

import java.util.Arrays;
import java.util.List;
import org.example.ItemCatalogDatabase;
import org.example.domain.value.Item;

public class ItemRepository {

  public List<Item> findByIds(List<String> ids) {
    return Arrays.stream(ItemCatalogDatabase.values()).filter(v -> ids.contains(v.id)).map(v -> new Item(v.id,
        v.price)).toList();
  }
}
