package org.example.domain.value;

import org.example.domain.DomainException;

/**
 * 支払い情報：金額クラス
 */
public class Money {
  private final int value;

  private static final int MONEY_MAX_VALUE = 1000000;

  public Money(String value) {
    this.value = Integer.parseInt(value);

    if (this.value > MONEY_MAX_VALUE) {
      throw new DomainException("支払い情報：金額が1,000,000円を超えているため、扱うことができません。");
    }
  }

  public int getValue() {
    return value;
  }
}
