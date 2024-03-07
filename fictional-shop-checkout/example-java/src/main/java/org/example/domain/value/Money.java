package org.example.domain.value;

/**
 * 支払い情報：金額クラス
 */
public class Money {
  private final int value;

  public Money(String value) {
    this.value = Integer.parseInt(value);
  }

  public int getValue() {
    return value;
  }
}
