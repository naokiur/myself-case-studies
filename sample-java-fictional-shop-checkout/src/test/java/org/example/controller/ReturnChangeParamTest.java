package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;

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
}