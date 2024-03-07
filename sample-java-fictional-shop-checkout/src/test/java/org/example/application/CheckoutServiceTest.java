package org.example.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.example.ItemCatalogDatabase;
import org.example.repository.ItemRepository;
import org.junit.jupiter.api.Test;

class CheckoutServiceTest {

  @Test
  void 空想題材具体例_識別番号の商品を2品購入時にお釣り情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetCodes = Arrays.asList(
        ItemCatalogDatabase.HAM.id,
        ItemCatalogDatabase.ORANGE_JUICE.id
    );
    var targetMoney = "400";

    assertEquals(50, service.returnChange(targetCodes, Map.of(), targetMoney));
  }

  @Test
  void 空想題材具体例_appendix_direct_識別番号と種別番号の両方の買い方が可能でありお釣り情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetCodes = List.of(
        ItemCatalogDatabase.HAM.id
    );
    var targetDirects = Map.of(
        "831",
        "120"
    );
    var targetMoney = "400";

    assertEquals(30, service.returnChange(targetCodes, targetDirects, targetMoney));
  }

  @Test
  void 空想題材具体例_appendix_direct_種別番号のみ買い方および複数購入が可能かでありお釣り情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetDirects = Map.of(
        "029",
        "300",
        "831",
        "120"
    );
    var targetMoney = "500";

    assertEquals(80, service.returnChange(List.of(), targetDirects, targetMoney));
  }
}