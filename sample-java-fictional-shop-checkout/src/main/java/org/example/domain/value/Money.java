package org.example.domain.value;

public class Money {
  private final int value;

  public Money(String value) {
    this.value = Integer.parseInt(value);
  }

  public int getValue() {
    return value;
  }
}
