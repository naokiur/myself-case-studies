package org.example.domain.value

import org.example.domain.DomainException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MoneyTest {

    @Test
    fun `数字でないとき例外が発生すること`() {
        val param = "abc"

        // HACK: DomainExceptionに寄せたほうが良い？
        assertFailsWith<NumberFormatException> { Money(param) }
    }

    @Test
    fun `値が1000000を超えるとき例外が発生すること`() {
        val param = "1000001"

        val e = assertFailsWith<DomainException> { Money(param) }
        assertEquals("支払い情報：金額が1,000,000円を超えているため、扱うことができません。", e.message)
    }
}