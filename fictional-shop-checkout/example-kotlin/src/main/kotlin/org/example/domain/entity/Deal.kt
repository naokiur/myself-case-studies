package org.example.domain.entity

import org.example.domain.DomainException
import org.example.domain.value.DirectItem
import org.example.domain.value.Item
import org.example.domain.value.Money
import org.example.domain.value.RequestCodeItem
import kotlin.math.abs

/**
 * 取引クラス
 * 1取引（1人の顧客による、商品をレジ店員へ渡しお釣りを受け取るまでを表す）は、商品情報と支払い情報を保持する。
 */
class Deal(
    private val codeItems: RequestCodeItem,
    private val directItems: List<DirectItem>,
    private val money: Money
) {
    data class DealResult(val price: Int, val message: String)

    companion object {
        private const val DEAL_SIZE_MAX_VALUE = 100
        private const val DEAL_PRICE_MAX_VALUE = 1_000_000
        private const val DEAL_NEED_STAMP_VALUE = 30_000
        private const val MESSAGE_FORMAT_LACK_MONEY = "取引情報：商品の合計金額よりも支払い情報が%d円不足しています。お客様に確認してください。"
    }

    init {
        if (codeItems.values.size + directItems.size > DEAL_SIZE_MAX_VALUE) {
            throw DomainException("取引情報：合計商品数が100を超えているため、扱うことができません。")
        }
    }

    /** お釣りを計算する */
    fun charge(): DealResult {
        val sumOfItems = codeItems.values.sumOf(Item::price)
        val sumOfDirectItems = directItems.sumOf(DirectItem::price)
        val sumOfDeal = sumOfItems + sumOfDirectItems

        if (sumOfDeal > DEAL_PRICE_MAX_VALUE) {
            throw DomainException("取引情報：合計金額が1,000,000円を超えているため、扱うことができません。")
        }

        val charge = money.value - sumOfDeal
        if (charge < 0) {
            throw DomainException(MESSAGE_FORMAT_LACK_MONEY.format(abs(charge)))
        }

        if (sumOfDeal > DEAL_NEED_STAMP_VALUE) {
            return DealResult(charge, "レジ店員の方へ：収入印紙200円を1枚貼付してください。")
        }
        return DealResult(charge, "")
    }
}