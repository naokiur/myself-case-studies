package org.example

import org.example.application.CheckoutService
import org.example.controller.CheckoutController
import org.example.repository.ItemRepository
import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)
    val firstParam = sc.next()
    val secondParam = sc.next()
    val thirdParam = sc.next()

    println("first: $firstParam")
    println("second: $secondParam")
    println("third: $thirdParam")

    val repository = ItemRepository()
    val service = CheckoutService(repository)
    val controller = CheckoutController(service)

    val result = controller.returnChange(firstParam, secondParam, thirdParam)
    println("result: $result")
}