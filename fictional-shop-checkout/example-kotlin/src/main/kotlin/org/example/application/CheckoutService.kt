package org.example.application

import org.example.domain.entity.Deal
import org.example.domain.entity.Deal.DealResult
import org.example.domain.value.DirectItem
import org.example.domain.value.Money
import org.example.domain.value.RequestCodeItem
import org.example.repository.ItemRepository

/**
 * 支払いサービスクラス
 */
class CheckoutService(private val itemRepository: ItemRepository) {

    /**
     * お釣りを返却する。
     */
    fun returnChange(paramCodes: List<String>, paramDirect: List<String>, paramMoney: String): DealResult {
        val items = RequestCodeItem(itemRepository.findByIds(paramCodes), paramCodes)
        val directItems = paramDirect.map { DirectItem(it) }
        val money = Money(paramMoney)
        val deal = Deal(items, directItems, money)
        return deal.charge()
    }
}