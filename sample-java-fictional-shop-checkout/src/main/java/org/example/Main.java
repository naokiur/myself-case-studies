package org.example;

import java.util.Scanner;
import org.example.application.CheckoutService;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String param = sc.next();

        var service = new CheckoutService();
        service.calculate(param);
    }
}