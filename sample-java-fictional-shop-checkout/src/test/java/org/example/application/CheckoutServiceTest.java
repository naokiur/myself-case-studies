package org.example.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Map;
import org.example.ItemCatalogDatabase;
import org.example.repository.ItemRepository;
import org.junit.jupiter.api.Test;

class CheckoutServiceTest {

  @Test
  void 商品が空想題材具体例_現金が十分であるときお釣り情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetCodes = Arrays.asList(
        ItemCatalogDatabase.HAM.id,
        ItemCatalogDatabase.ORANGE_JUICE.id
    );
    var targetMoney = "400";

    assertEquals(50, service.returnChange(targetCodes, Map.of(), targetMoney));
  }
}