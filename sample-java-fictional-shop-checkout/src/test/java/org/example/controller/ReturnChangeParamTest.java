package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReturnChangeParamTest {

  @Test
  void パラメータ生成確認_1行目が1つ_2行目が1つ_3行目が1つである場合クラスを生成できること() {
    var firstParam = "001";
    var secondParam = "831:100";
    var thirdParam = "200";
    var target = new ReturnChangeParam(firstParam, secondParam, thirdParam);

    assertEquals(List.of("001"), target.codes);
    assertTrue(target.directItems.containsKey("831"));
    assertEquals("100", target.directItems.get("831"));
    assertEquals("200", target.money);
  }

  @Test
  void パラメータ生成確認_1行目が2つ_2行目が0_3行目が1つである場合クラスを生成できること() {
    var firstParam = "001,002";
    var secondParam = "";
    var thirdParam = "200";
    var target = new ReturnChangeParam(firstParam, secondParam, thirdParam);

    assertEquals(List.of("001", "002"), target.codes);
    assertEquals(0, target.directItems.size());
    assertEquals("200", target.money);
  }

  @Test
  void パラメータ生成確認_1行目が0_2行目が2つ_3行目が1つである場合クラスを生成できること() {
    var firstParam = "";
    var secondParam = "831:200,220:300";
    var thirdParam = "200";
    var target = new ReturnChangeParam(firstParam, secondParam, thirdParam);

    assertEquals(0, target.codes.size());
    assertTrue(target.directItems.containsKey("831"));
    assertTrue(target.directItems.containsKey("220"));
    assertEquals("200", target.directItems.get("831"));
    assertEquals("300", target.directItems.get("220"));
    assertEquals("200", target.money);
  }


  @Test
  void パラメータ生成確認_1行目が2_2行目が2つ_3行目が1つである場合クラスを生成できること() {
    var firstParam = "001,002";
    var secondParam = "831:200,220:300";
    var thirdParam = "200";
    var target = new ReturnChangeParam(firstParam, secondParam, thirdParam);

    assertEquals(List.of("001", "002"), target.codes);
    assertTrue(target.directItems.containsKey("831"));
    assertTrue(target.directItems.containsKey("220"));
    assertEquals("200", target.directItems.get("831"));
    assertEquals("300", target.directItems.get("220"));
    assertEquals("200", target.money);
  }

  @Test
  void 期待と異なる形式がパラメータの場合も生成可能ではあること() {
    var firstParam = "001,002,,,test,,";
    var secondParam = "abcabcabc,abc:abcddd";
    var thirdParam = "abc";
    var target = new ReturnChangeParam(firstParam, secondParam, thirdParam);

    assertEquals(List.of("001", "002", "test"), target.codes);
    assertEquals(1, target.directItems.size());
    assertTrue(target.directItems.containsKey("abc"));
    assertEquals("abcddd", target.directItems.get("abc"));
    assertEquals("abc", target.money);
  }
}