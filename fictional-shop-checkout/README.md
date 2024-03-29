# 空想ビジネス：顧客とレジ店員間における商品と金銭のやりとり

## 内容

### 登場人物
* レジ店員
* 顧客
* システム（本アプリケーション）
* 外部システム（仮想の入力情報保持アプリケーション）

### レジ店員と顧客が扱う情報

* 商品情報
    * 識別番号（値段はシステム側が保持している情報を活用する）
* 支払い情報
    * 現金

### ユースケース

* フロー 
    * 顧客がレジ店員へ商品を渡す
    * レジ店員が外部システムへ商品情報を入力する
    * 顧客が支払いを伝え必要な資材をレジ店員へ渡す
    * レジ店員が支払いに必要な資材の情報を外部システムへ入力する
        * 入力する情報
            * 商品情報（識別番号）
            * 支払い情報
    * 外部システムがシステムへ連携する
        * 連携する情報
            * 商品情報（識別番号）
            * 支払い情報
        * 入力した内容を元に、合計金額とお釣りを算出する
            * CLIアプリケーションだが、イメージとしてはレジ端末からAPIが送信されて受け取ったあとの処理
    * 充足している場合
        * レジ店員がシステムからお釣り情報を取得する
        * レジ店員が結果を元にお釣りを顧客に返金する
    * 不足している場合
        * レジ店員がシステムから不足情報を取得する
        * レジ店員が不足している旨を顧客に連絡する
* 具体例
    * 2つの商品を現金で購入する
        1. 顧客が以下商品の購入意思をレジ店員へ伝える
            * ハム（加工肉）
                * 識別番号
                    * 001
            * オレンジジュース
                * 識別番号
                    * 002
        2. レジ店員が外部システムに受領した商品を登録する
        3. 顧客が対応する現金をレジ店員へ渡す
        4. レジ店員が外部システムに受領した現金を登録する
        5. レジ店員が外部システムで確定を実施する
        6. 外部システムがシステムへ商品情報と支払い情報を連携する
        7. システムが外部システムへお釣り情報を連携する
        8. 外部システムがレジ店員へお釣り情報を連携する
        9. レジ店員が外部システムよりお釣り情報を元に現金を取得する
        10. レジ店員が顧客へ現金を返却する



### 全体イメージ

![全体イメージ](doc/overall_picture.png)