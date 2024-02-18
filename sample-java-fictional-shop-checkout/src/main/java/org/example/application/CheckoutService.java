package org.example.application;

import java.util.List;
import org.example.domain.entity.Deal;
import org.example.domain.value.Money;
import org.example.repository.ItemRepository;

public class CheckoutService {

  public int returnChange(List<String> paramCodes, String paramMoney) {
    var repository = new ItemRepository();
    // TECH: N+1
    var items = paramCodes.stream().map(repository::getById).toList();
    var money = new Money(paramMoney);

    var deal = new Deal(items, money);
    return deal.charge();
  }
}
