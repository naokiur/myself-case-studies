package org.example.controller

/**
 * お釣り処理のためのパラメータクラス
 */
internal class ReturnChangeParam(
    firstParam: String,
    secondParam: String,
    thirdParam: String
) {
    val codes: List<String>
    val directItems: List<String>
    val money: String

    companion object {
        private const val PARAM_ROW_SEPARATE = ","
        private const val PARAM_KEY_VALUE_SEPARATE = ":"
    }

    init {
        // 1行目: 識別番号（カンマ区切り）
        val rawCodes = firstParam.split(PARAM_ROW_SEPARATE).filter { it.isNotEmpty() }
        val codesPartition = rawCodes.partition { it.length > 100 }
        if (codesPartition.first.isNotEmpty()) {
            throw ParametersException("1行目のパラメータ（識別番号）に100文字を超えた文字列が含まれているため、扱うことができません。")
        }
        if (codesPartition.second.size > 50) {
            throw ParametersException("1行目のパラメータ（識別番号）の個数が50個を超えているため、扱うことができません。")
        }

        // 2行目: 直打ち（種別番号:値段 のカンマ区切り）
        val rawDirect = secondParam.split(PARAM_ROW_SEPARATE)
            .filter { it.isNotEmpty() }
            .filter { it.contains(PARAM_KEY_VALUE_SEPARATE) }
        val directPartition = rawDirect.partition {
            // "xxx:yyy" の左側が101文字以上ならNG
            val typeId = it.substring(0, it.indexOf(PARAM_KEY_VALUE_SEPARATE))
            typeId.length > 100
        }
        if (directPartition.first.isNotEmpty()) {
            throw ParametersException("2行目のパラメータ（種別番号:値段）の1つ目（種別番号）に100文字を超えた文字列が含まれてるため、扱うことができません。")
        }
        if (directPartition.second.size > 50) {
            throw ParametersException("2行目のパラメータ（種別番号:値段）の個数が50個を超えているため、扱うことができません。")
        }

        this.codes = codesPartition.second
        this.directItems = directPartition.second
        this.money = thirdParam
    }
}