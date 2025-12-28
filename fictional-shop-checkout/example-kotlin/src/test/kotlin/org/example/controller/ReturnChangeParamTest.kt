package org.example.controller

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ReturnChangeParamTest {

    @Test
    fun `パラメータ生成確認_1行目が1つ_2行目が1つ_3行目が1つである場合クラスを生成できること`() {
        val firstParam = "001"
        val secondParam = "831:100"
        val thirdParam = "200"
        val target = ReturnChangeParam(firstParam, secondParam, thirdParam)

        assertEquals(listOf("001"), target.codes)
        assertEquals(listOf("831:100"), target.directItems)
        assertEquals("200", target.money)
    }

    @Test
    fun `パラメータ生成確認_1行目が2つ_2行目が0_3行目が1つである場合クラスを生成できること`() {
        val firstParam = "001,002"
        val secondParam = ""
        val thirdParam = "200"
        val target = ReturnChangeParam(firstParam, secondParam, thirdParam)

        assertEquals(listOf("001", "002"), target.codes)
        assertEquals(0, target.directItems.size)
        assertEquals("200", target.money)
    }

    @Test
    fun `パラメータ生成確認_1行目が0_2行目が2つ_3行目が1つである場合クラスを生成できること`() {
        val firstParam = ""
        val secondParam = "831:200,220:300"
        val thirdParam = "200"
        val target = ReturnChangeParam(firstParam, secondParam, thirdParam)

        assertEquals(0, target.codes.size)
        assertEquals(listOf("831:200", "220:300"), target.directItems)
        assertEquals("200", target.money)
    }

    @Test
    fun `パラメータ生成確認_1行目が2_2行目が2つ_3行目が1つである場合クラスを生成できること`() {
        val firstParam = "001,002"
        val secondParam = "831:200,220:300"
        val thirdParam = "200"
        val target = ReturnChangeParam(firstParam, secondParam, thirdParam)

        assertEquals(listOf("001", "002"), target.codes)
        assertEquals(listOf("831:200", "220:300"), target.directItems)
        assertEquals("200", target.money)
    }

    @Test
    fun `期待と異なる形式がパラメータの場合も生成可能ではあること`() {
        val firstParam = "001,002,,,test,,"
        val secondParam = "abcabcabc,abc:abcddd"
        val thirdParam = "abc"
        val target = ReturnChangeParam(firstParam, secondParam, thirdParam)

        assertEquals(listOf("001", "002", "test"), target.codes)
        assertEquals(listOf("abc:abcddd"), target.directItems)
        assertEquals("abc", target.money)
    }

    @Test
    fun `パラメータ異常_1行目の1つの文字数が100文字を超えるときパラメータ例外を発生させること`() {
        val firstParam = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891"
        val secondParam = "831:200"
        val thirdParam = "200"

        val e = assertFailsWith<ParametersException> { ReturnChangeParam(firstParam, secondParam, thirdParam) }
        assertEquals("1行目のパラメータ（識別番号）に100文字を超えた文字列が含まれているため、扱うことができません。", e.message)
    }

    @Test
    fun `パラメータ異常_1行目の個数が50個を超えるときパラメータ例外を発生させること`() {
        val firstParam = "001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001,001"
        val secondParam = "831:200"
        val thirdParam = "200"

        val e = assertFailsWith<ParametersException> { ReturnChangeParam(firstParam, secondParam, thirdParam) }
        assertEquals("1行目のパラメータ（識別番号）の個数が50個を超えているため、扱うことができません。", e.message)
    }

    @Test
    fun `パラメータ異常_2行目の1つ目の文字列_種別番号が100文字を超えるときパラメータ例外を発生させること`() {
        val firstParam = ""
        val secondParam = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891:200"
        val thirdParam = "200"

        val e = assertFailsWith<ParametersException> { ReturnChangeParam(firstParam, secondParam, thirdParam) }
        assertEquals("2行目のパラメータ（種別番号:値段）の1つ目（種別番号）に100文字を超えた文字列が含まれてるため、扱うことができません。", e.message)
    }

    @Test
    fun `パラメータ異常_2行目の個数が50個を超えるときパラメータ例外を発生させること`() {
        val firstParam = ""
        val secondParam = "831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200,831:200"
        val thirdParam = "200"

        val e = assertFailsWith<ParametersException> { ReturnChangeParam(firstParam, secondParam, thirdParam) }
        assertEquals("2行目のパラメータ（種別番号:値段）の個数が50個を超えているため、扱うことができません。", e.message)
    }
}