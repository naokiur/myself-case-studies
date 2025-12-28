package org.example.domain.entity

import org.example.domain.DomainException
import org.example.domain.value.DirectItem
import org.example.domain.value.Item
import org.example.domain.value.Money
import org.example.domain.value.RequestCodeItem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DealTest {

    @Test
    fun `合計商品数が100を超えるとき例外が発生すること`() {
        val targetItems = RequestCodeItem(
            listOf(
                Item("001", 250),
                Item("002", 100)
            ),
            listOf("001", "002")
        )
        val targetDirectItems = (0 until 100).map { DirectItem("00$it", 250) }
        val targetMoney = Money("1000")

        val e = assertFailsWith<DomainException> { Deal(targetItems, targetDirectItems, targetMoney) }
        assertEquals("取引情報：合計商品数が100を超えているため、扱うことができません。", e.message)
    }

    @Test
    fun `charge_識別番号の結果350円の商品に対して支払いを400円渡しお釣りが50円であること`() {
        val targetItems = RequestCodeItem(
            listOf(
                Item("001", 250),
                Item("002", 100)
            ),
            listOf("001", "002")
        )
        val targetMoney = Money("400")
        val deal = Deal(targetItems, emptyList(), targetMoney)

        assertEquals(50, deal.charge().price)
    }

    @Test
    fun `charge_識別番号001_種別番号900_300円の結果550円の商品に対して支払いを1000円渡しお釣りが450円であること`() {
        val targetItems = RequestCodeItem(
            listOf(Item("001", 250)),
            listOf("001")
        )
        val targetDirectItems = listOf(
            DirectItem("900", 300)
        )
        val targetMoney = Money("1000")
        val deal = Deal(targetItems, targetDirectItems, targetMoney)

        assertEquals(450, deal.charge().price)
    }

    @Test
    fun `charge_識別番号001と002_種別番号900_300円と800_200円の結果850円の商品に対して支払いを1000円渡しお釣りが150円であること`() {
        val targetItems = RequestCodeItem(
            listOf(
                Item("001", 250),
                Item("002", 100)
            ),
            listOf("001", "002")
        )
        val targetDirectItems = listOf(
            DirectItem("900", 300),
            DirectItem("800", 200)
        )
        val targetMoney = Money("1000")
        val deal = Deal(targetItems, targetDirectItems, targetMoney)

        assertEquals(150, deal.charge().price)
    }

    @Test
    fun `charge_合計金額が1000000を超えるとき例外が発生すること`() {
        val targetItems = RequestCodeItem(
            emptyList(),
            emptyList()
        )
        val targetDirectItems = (0 until 99).map { DirectItem("00$it", 10000) }.toMutableList()
        val lastDirectItem = DirectItem("last", 10001)
        targetDirectItems.add(lastDirectItem)

        val targetMoney = Money("1000000")
        val deal = Deal(targetItems, targetDirectItems, targetMoney)

        val e = assertFailsWith<DomainException> { deal.charge() }
        assertEquals("取引情報：合計金額が1,000,000円を超えているため、扱うことができません。", e.message)
    }

    @Test
    fun `charge_合計金額よりも支払い情報が少ないとき例外が発生すること`() {
        val targetItems = RequestCodeItem(
            listOf(
                Item("001", 250),
                Item("002", 100)
            ),
            listOf("001", "002")
        )
        val targetDirectItems = listOf(
            DirectItem("900", 300),
            DirectItem("800", 200)
        )
        val targetMoney = Money("500")
        val deal = Deal(targetItems, targetDirectItems, targetMoney)

        val e = assertFailsWith<DomainException> { deal.charge() }
        assertEquals("取引情報：商品の合計金額よりも支払い情報が350円不足しています。お客様に確認してください。", e.message)
    }
}