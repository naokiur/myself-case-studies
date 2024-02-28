package org.example.application;

import java.util.List;
import org.example.domain.entity.Deal;
import org.example.domain.value.Money;
import org.example.repository.ItemRepository;

public class CheckoutService {

  private final ItemRepository itemRepository;

  public CheckoutService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public int returnChange(List<String> paramCodes, String paramMoney) {
    // TECH: N+1
    var items = this.itemRepository.findByIds(paramCodes);
    var money = new Money(paramMoney);
    var deal = new Deal(items, money);

    return deal.charge();
  }
}
