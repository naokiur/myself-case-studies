package org.example.domain.value

import org.example.domain.DomainException

/**
 * 購入希望の識別番号 商品情報のリスト
 */
class RequestCodeItem(val values: List<Item>, requestCodes: List<String>) {
    init {
        if (values.size < requestCodes.size) {
            throw DomainException("識別番号商品：存在しない識別番号が含まれています。最初から登録しなおしてください。")
        }
    }
}