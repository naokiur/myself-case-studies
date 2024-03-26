package org.example.domain.value;

import java.util.List;
import org.example.domain.DomainException;

/**
 * 購入希望の識別番号 商品情報のリスト
 */
public class RequestCodeItem {

  private final List<Item> values;

  public RequestCodeItem(List<Item> values, List<String> requestCodes) {
    // HACK: 現状コンストラクタでのみ活用するため、requestCodesはフィールドに保持しない。
    // 必要になったタイミングでフィールド追加する
    if (values.size() < requestCodes.size()) {
      throw new DomainException("識別番号商品：存在しない識別番号が含まれています。最初から登録しなおしてください。");
    }
    this.values = values;
  }

  public List<Item> getValues() {
    return values;
  }
}
