package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    assertEquals(List.of("831:100"), target.directItems);
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
    assertEquals(List.of("831:200", "220:300"), target.directItems);
    assertEquals("200", target.money);
  }


  @Test
  void パラメータ生成確認_1行目が2_2行目が2つ_3行目が1つである場合クラスを生成できること() {
    var firstParam = "001,002";
    var secondParam = "831:200,220:300";
    var thirdParam = "200";
    var target = new ReturnChangeParam(firstParam, secondParam, thirdParam);

    assertEquals(List.of("001", "002"), target.codes);
    assertEquals(List.of("831:200", "220:300"), target.directItems);
    assertEquals("200", target.money);
  }

  @Test
  void 期待と異なる形式がパラメータの場合も生成可能ではあること() {
    var firstParam = "001,002,,,test,,";
    var secondParam = "abcabcabc,abc:abcddd";
    var thirdParam = "abc";
    var target = new ReturnChangeParam(firstParam, secondParam, thirdParam);

    assertEquals(List.of("001", "002", "test"), target.codes);
    assertEquals(List.of("abc:abcddd"), target.directItems);
    assertEquals("abc", target.money);
  }

  @Test
  void パラメータ異常_1行目の1つの文字数が100文字を超えるときパラメータ例外を発生させること() {
    var firstParam = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891";
    var secondParam = "831:200";
    var thirdParam = "200";

    ParametersException e = assertThrows(ParametersException.class, () -> new ReturnChangeParam(firstParam, secondParam, thirdParam));
    assertEquals("1行目のパラメータ（識別番号）に100文字を超えた文字列が含まれているため、扱うことができません。", e.getMessage());
  }

  @Test
  void パラメータ異常_1行目の個数が50個を超えるときパラメータ例外を発生させること() {
    var firstParam = "001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001";
    var secondParam = "831:200";
    var thirdParam = "200";

    ParametersException e = assertThrows(ParametersException.class, () -> new ReturnChangeParam(firstParam, secondParam, thirdParam));
    assertEquals("1行目のパラメータ（識別番号）の個数が50個を超えているため、扱うことができません。", e.getMessage());
  }

  @Test
  void パラメータ異常_2行目の1つ目の文字列_種別番号が100文字を超えるときパラメータ例外を発生させること() {
    var firstParam = "";
    var secondParam = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891:200";
    var thirdParam = "200";

    ParametersException e = assertThrows(ParametersException.class, () -> new ReturnChangeParam(firstParam, secondParam, thirdParam));
    assertEquals("2行目のパラメータ（種別番号:値段）の1つ目（種別番号）に100文字を超えた文字列が含まれてるため、扱うことができません。", e.getMessage());
  }

  @Test
  void パラメータ異常_2行目の個数が50個を超えるときパラメータ例外を発生させること() {
    var firstParam = "";
    var secondParam = "831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200";
    var thirdParam = "200";

    ParametersException e = assertThrows(ParametersException.class, () -> new ReturnChangeParam(firstParam, secondParam, thirdParam));
    assertEquals("2行目のパラメータ（種別番号:値段）の個数が50個を超えているため、扱うことができません。", e.getMessage());
  }
}