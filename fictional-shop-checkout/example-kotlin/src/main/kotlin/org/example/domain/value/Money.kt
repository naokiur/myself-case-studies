package org.example.domain.value

import org.example.domain.DomainException

/**
 * 支払い情報：金額クラス
 */
class Money(value: String) {
    val value: Int

    companion object {
        private const val MONEY_MAX_VALUE = 1_000_000
    }

    init {
        this.value = value.toInt()
        if (this.value > MONEY_MAX_VALUE) {
            throw DomainException("支払い情報：金額が1,000,000円を超えているため、扱うことができません。")
        }
    }
}
