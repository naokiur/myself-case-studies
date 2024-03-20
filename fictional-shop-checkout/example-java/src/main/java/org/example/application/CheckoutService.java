package org.example.application;

import java.util.List;
import org.example.domain.entity.Deal;
import org.example.domain.entity.Deal.DealResult;
import org.example.domain.value.DirectItem;
import org.example.domain.value.Money;
import org.example.repository.ItemRepository;

/**
 * 支払いサービスクラス
 */
public class CheckoutService {

  private final ItemRepository itemRepository;

  public CheckoutService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  /**
   * お釣りを返却する。
   * @param paramCodes 識別番号のパラメータ
   * @param paramDirect 直打ち商品のパラメータ
   * @param paramMoney 支払い情報: 金額のパラメータ
   * @return お釣りの金額
   */
  public DealResult returnChange(List<String> paramCodes, List<String> paramDirect, String paramMoney) {
    var items = this.itemRepository.findByIds(paramCodes);
    var directItems = paramDirect.stream().map(DirectItem::new).toList();
    var money = new Money(paramMoney);

    var deal = new Deal(items, directItems, money);

    return deal.charge();
  }
}
