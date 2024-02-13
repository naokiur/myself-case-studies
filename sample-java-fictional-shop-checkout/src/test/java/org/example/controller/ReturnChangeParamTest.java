package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReturnChangeParamTest {

  @Test
  void 一行目のパラメータが1つ二行目が1つである場合クラスを生成できること() {
    var target = new ReturnChangeParam("100", "200");

    assertEquals(List.of("100"), target.codes);
    assertEquals("200", target.money);
  }
  @Test
  void 一行目のパラメータが2つ二行目が1つである場合クラスを生成できること() {
    var target = new ReturnChangeParam("100,200", "200");

    assertEquals(List.of("100", "200"), target.codes);
    assertEquals("200", target.money);
  }
}