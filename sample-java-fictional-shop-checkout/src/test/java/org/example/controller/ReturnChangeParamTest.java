package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReturnChangeParamTest {

  @Test
  void 一行目のパラメータが1つ二行目が1つである場合クラスを生成できること() {
    var firstParam = "001";
    var secondParam = "200";
    var target = new ReturnChangeParam(firstParam, secondParam);

    assertEquals(List.of("001"), target.codes);
    assertEquals("200", target.money);
  }
  @Test
  void 一行目のパラメータが2つ二行目が1つである場合クラスを生成できること() {
    var firstParam = "001,002";
    var secondParam = "200";
    var target = new ReturnChangeParam(firstParam, secondParam);

    assertEquals(List.of("001", "002"), target.codes);
    assertEquals("200", target.money);
  }
  @Test
  void 期待と異なる形式がパラメータの場合も生成可能ではあること() {
    var firstParam = "001,002,,,test,,";
    var secondParam = "abc";
    var target = new ReturnChangeParam(firstParam, secondParam);

    assertEquals(List.of("001", "002", "test"), target.codes);
    assertEquals("abc", target.money);
  }
}