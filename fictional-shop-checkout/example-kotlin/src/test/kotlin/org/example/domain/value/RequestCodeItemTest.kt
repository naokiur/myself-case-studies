package org.example.domain.value

import org.example.ItemCatalogDatabase
import org.example.domain.DomainException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RequestCodeItemTest {

    @Test
    fun `購入希望の識別番号数と識別番号商品数が等しい_識別番号商品情報のクラスを生成できること`() {
        val targetItems = listOf(
            Item(ItemCatalogDatabase.HAM.id, ItemCatalogDatabase.HAM.price),
            Item(ItemCatalogDatabase.ORANGE_JUICE.id, ItemCatalogDatabase.ORANGE_JUICE.price)
        )
        val targetCodes = listOf(
            ItemCatalogDatabase.HAM.id,
            ItemCatalogDatabase.ORANGE_JUICE.id
        )

        val requestCodeItems = RequestCodeItem(targetItems, targetCodes)

        assertEquals(targetCodes.size, requestCodeItems.values.size)
        assertEquals(targetItems.size, requestCodeItems.values.size)
        assertEquals(targetItems, requestCodeItems.values)
    }

    @Test
    fun `購入希望の識別番号数と識別番号商品数が0_識別番号商品情報のクラスを生成できること`() {
        val targetItems = emptyList<Item>()
        val targetCodes = emptyList<String>()

        val requestCodeItems = RequestCodeItem(targetItems, targetCodes)

        assertEquals(targetCodes.size, requestCodeItems.values.size)
        assertEquals(targetItems.size, requestCodeItems.values.size)
        assertEquals(targetItems, requestCodeItems.values)
    }

    @Test
    fun `購入希望の識別番号数より識別番号商品数が少ない_存在しない識別番号が入力されたとみなし識別番号商品情報のクラスを生成できないこと`() {
        val targetItems = listOf(
            Item(ItemCatalogDatabase.HAM.id, ItemCatalogDatabase.HAM.price)
        )
        val targetCodes = listOf(
            ItemCatalogDatabase.HAM.id,
            ItemCatalogDatabase.ORANGE_JUICE.id
        )

        val e = assertFailsWith<DomainException> { RequestCodeItem(targetItems, targetCodes) }
        assertEquals("識別番号商品：存在しない識別番号が含まれています。最初から登録しなおしてください。", e.message)
    }
}