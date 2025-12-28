package org.example.controller

import org.example.application.CheckoutService

/**
 * 支払いコントローラクラス
 * 受領したパラメータを元にサービスクラスに処理を渡す責務を担う
 */
class CheckoutController(private val checkoutService: CheckoutService) {

    /**
     * パラメータを受け取り、そのパラメータに応じたお釣りを返却する
     */
    fun returnChange(firstRawParam: String, secondRawParam: String, thirdRawParam: String): KReturnChangeResult {
        val params = ReturnChangeParam(firstRawParam, secondRawParam, thirdRawParam)
        val result = checkoutService.returnChange(params.codes, params.directItems, params.money)
        return KReturnChangeResult(result.price, result.message)
    }
}