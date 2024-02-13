package org.example;

import java.util.Scanner;
import org.example.controller.CheckoutController;

public class Main {

  public static void main(String[] args) {
    var sc = new Scanner(System.in);
    // 1行目：商品情報：バーコード情報カンマ区切り
    var firstParam = sc.next();
    // 2行目：支払い情報：金額
    var secondParam = sc.next();

    System.out.println("first: " + firstParam);
    System.out.println("second: " + secondParam);

    var controller = new CheckoutController();
    var result = controller.returnChange(firstParam, secondParam);

    System.out.println("result: " + result);
  }
}