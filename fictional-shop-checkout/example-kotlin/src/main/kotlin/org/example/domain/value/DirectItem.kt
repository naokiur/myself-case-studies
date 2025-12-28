package org.example.domain.value

import org.example.domain.DomainException

/**
 * 商品情報（直打ち）
 */
class DirectItem {
    /** 商品の金額 */
    val price: Int
    /** 種別番号 */
    val typeId: String

    companion object {
        private const val PRICE_MAX_VALUE = 1_000_000
    }

    constructor(typeId: String, price: Int) {
        this.typeId = typeId
        this.price = price
        if (this.price > PRICE_MAX_VALUE) {
            throw DomainException("直打ち商品：金額が1,000,000円を超えているため、扱うことができません。")
        }
    }

    constructor(typeIdAndPrice: String) {
        val values = typeIdAndPrice.split(":")
        val rawTypeId = values[0]
        val rawPrice = values[1]
        this.typeId = rawTypeId
        this.price = rawPrice.toInt()
        if (this.price > PRICE_MAX_VALUE) {
            throw DomainException("直打ち商品：金額が1,000,000円を超えているため、扱うことができません。")
        }
    }
}