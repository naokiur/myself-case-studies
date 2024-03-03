package org.example.repository;

import java.util.Arrays;
import java.util.List;
import org.example.ItemCatalogDatabase;
import org.example.domain.value.Item;

/**
 * 商品リポジトリクラス
 * 商品情報をデータベース・データソースから取得する責務を担う
 */
public class ItemRepository {

  /**
   * 商品の識別番号に基づく商品情報をベータベース・データソースから取得する
   * @param ids 商品の識別番号のリスト
   * @return 商品情報のリスト
   */
  public List<Item> findByIds(List<String> ids) {
    return Arrays.stream(ItemCatalogDatabase.values()).filter(v -> ids.contains(v.id)).map(v -> new Item(v.id,
        v.price)).toList();
  }
}
