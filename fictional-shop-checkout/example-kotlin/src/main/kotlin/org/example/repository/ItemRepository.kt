package org.example.repository

import org.example.ItemCatalogDatabase
import org.example.domain.value.Item

/**
 * 商品リポジトリクラス
 * 商品情報をデータベース・データソースから取得する責務を担う
 */
class ItemRepository {
    /**
     * 商品の識別番号に基づく商品情報をデータベース・データソースから取得する
     */
    fun findByIds(ids: List<String>): List<Item> =
        ItemCatalogDatabase.values().filter { ids.contains(it.id) }.map { Item(it.id, it.price) }
}