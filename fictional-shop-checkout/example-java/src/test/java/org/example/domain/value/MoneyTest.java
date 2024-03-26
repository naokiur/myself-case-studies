package org.example.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.domain.DomainException;
import org.junit.jupiter.api.Test;

class MoneyTest {

  @Test
  void 数字でないとき例外が発生すること() {
    var param = "abc";

    // HACK: DomainExceptionに寄せたほうが良い？
    assertThrows(NumberFormatException.class, () -> new Money(param));
  }

  @Test
  void 値が1000000を超えるとき例外が発生すること() {
    var param = "1000001";

    DomainException e = assertThrows(DomainException.class, () -> new Money(param));
    assertEquals("支払い情報：金額が1,000,000円を超えているため、扱うことができません。", e.getMessage());
  }
}