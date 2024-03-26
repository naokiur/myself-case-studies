package org.example.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.example.ItemCatalogDatabase;
import org.example.domain.DomainException;
import org.junit.jupiter.api.Test;

class RequestCodeItemTest {

  @Test
  void 購入希望の識別番号数と識別番号商品数が等しい_識別番号商品情報のクラスを生成できること() {
    var targetItems = List.of(
        new Item(ItemCatalogDatabase.HAM.id, ItemCatalogDatabase.HAM.price),
        new Item(ItemCatalogDatabase.ORANGE_JUICE.id, ItemCatalogDatabase.ORANGE_JUICE.price)
    );
    var targetCodes = List.of(
        ItemCatalogDatabase.HAM.id,
        ItemCatalogDatabase.ORANGE_JUICE.id
    );

    var requestCodeItems = new RequestCodeItem(targetItems, targetCodes);

    assertEquals(targetCodes.size(), requestCodeItems.getValues().size());
    assertEquals(targetItems.size(), requestCodeItems.getValues().size());
    assertEquals(targetItems, requestCodeItems.getValues());
  }
  @Test
  void 購入希望の識別番号数と識別番号商品数が0_識別番号商品情報のクラスを生成できること() {
    var targetItems = new ArrayList<Item>();
    var targetCodes = new ArrayList<String>();

    var requestCodeItems = new RequestCodeItem(targetItems, targetCodes);

    assertEquals(targetCodes.size(), requestCodeItems.getValues().size());
    assertEquals(targetItems.size(), requestCodeItems.getValues().size());
    assertEquals(targetItems, requestCodeItems.getValues());
  }

  @Test
  void 購入希望の識別番号数より識別番号商品数が少ない_存在しない識別番号が入力されたとみなし識別番号商品情報のクラスを生成できないこと() {
    var targetItems = List.of(
        new Item(ItemCatalogDatabase.HAM.id, ItemCatalogDatabase.HAM.price)
    );
    var targetCodes = List.of(
        ItemCatalogDatabase.HAM.id,
        ItemCatalogDatabase.ORANGE_JUICE.id
    );

    DomainException e = assertThrows(DomainException.class, () -> new RequestCodeItem(targetItems, targetCodes));
    assertEquals("識別番号商品：存在しない識別番号が含まれています。最初から登録しなおしてください。", e.getMessage());
  }
}