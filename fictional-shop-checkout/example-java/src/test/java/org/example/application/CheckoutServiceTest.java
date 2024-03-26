package org.example.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.example.ItemCatalogDatabase;
import org.example.domain.DomainException;
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

    var result = service.returnChange(targetCodes, List.of(), targetMoney);
    assertEquals(50, result.price());
  }

  @Test
  void 空想題材具体例_appendix_direct_識別番号と種別番号の両方の買い方が可能でありお釣り情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetCodes = List.of(
        ItemCatalogDatabase.HAM.id
    );
    var targetDirects = List.of(
        "831:120"
    );
    var targetMoney = "400";

    var result = service.returnChange(targetCodes, targetDirects, targetMoney);
    assertEquals(30, result.price());
  }

  @Test
  void 空想題材具体例_appendix_direct_種別番号のみ買い方および複数購入が可能でありお釣り情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetDirects = List.of(
        "029:300",
        "831:120"
    );
    var targetMoney = "500";

    var result = service.returnChange(List.of(), targetDirects, targetMoney);
    assertEquals(80, result.price());
  }

  @Test
  void 空想題材具体例_appendix_direct_種別番号のみ買い方および複数かつ同一種別番号の購入が可能でありお釣り情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetDirects = List.of(
        "831:300",
        "831:120"
    );
    var targetMoney = "500";
    var result = service.returnChange(List.of(), targetDirects, targetMoney);

    assertEquals(80, result.price());
  }
  @Test
  void 空想題材具体例_appendix_stamp_種別番号のみ買い方で1つの商品を30000円以上購入_お釣り情報と収入印紙情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetDirects = List.of(
        "121:35000"
    );
    var targetMoney = "40000";

    var result =  service.returnChange(List.of(), targetDirects, targetMoney);
    assertEquals(5000, result.price());
    assertEquals("レジ店員の方へ：収入印紙200円を1枚貼付してください。", result.message());
  }
  @Test
  void 空想題材具体例_appendix_stamp_識別番号と種別番号の両方の買い方で2つの商品で合計を30000円以上購入_お釣り情報と収入印紙情報を返却できること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);
    var targetCodes = List.of(
        ItemCatalogDatabase.HAM.id
    );

    var targetDirects = List.of(
        "121:29900"
    );
    var targetMoney = "40000";

    var result =  service.returnChange(targetCodes, targetDirects, targetMoney);
    assertEquals(9850, result.price());
    assertEquals("レジ店員の方へ：収入印紙200円を1枚貼付してください。", result.message());
  }
  @Test
  void 空想題材具体例_appendix_stamp_誤って合計金額より少ない支払い情報をレジ店員に伝えたとき_誤りである旨が伝わること() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);
    var targetCodes = List.of(
        ItemCatalogDatabase.HAM.id
    );

    var targetDirects = List.of(
        "831:120"
    );
    var targetMoney = "120";

    DomainException e = assertThrows(DomainException.class, () -> service.returnChange(targetCodes, targetDirects, targetMoney));
    assertEquals(
        "取引情報：商品の合計金額よりも支払い情報が250円不足しています。お客様に確認してください。",
        e.getMessage());
  }

  @Test
  void 空想題材具体例_存在しない識別番号を入力する() {
    var repository = new ItemRepository();
    var service = new CheckoutService(repository);

    var targetCodes = List.of(
        "99999"
    );
    var targetMoney = "400";

    DomainException e = assertThrows(DomainException.class, () -> service.returnChange(targetCodes, List.of(), targetMoney));
    assertEquals(
        "識別番号商品：存在しない識別番号が含まれています。最初から登録しなおしてください。",
        e.getMessage());
  }
}