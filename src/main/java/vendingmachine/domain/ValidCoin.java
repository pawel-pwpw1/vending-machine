package vendingmachine.domain;

import static vendingmachine.domain.Money.money;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author bartosz walacik
 */
public enum ValidCoin {
  /**
   * 1 cent coin
   */
  PENNY(money(0.01), 1, 1),

  /**
   * 5 cents coin
   */
  NICKEL(money(0.05), 1, 5),

  /**
   * 10 cents coin
   */
  DIME(money(0.1), 2, 10),

  /**
   * 25 cents coin
   */
  QUARTER(money(0.25), 3, 25),
  ;

  private final Money money;
  private final int weight;
  private final int size;

  ValidCoin(Money money, int weight, int size) {
    this.money = money;
    this.weight = weight;
    this.size = size;
  }

  public Money getMoney() {
    return money;
  }

  public BigDecimal getValue() {
    return money.getValue();
  }

  public int getWeight() {
    return weight;
  }


  public int getSize() {
    return size;
  }

  public static Optional<ValidCoin> getFor(int weight, int size) {
    if (weight == PENNY.weight && size == PENNY.size) {
      return Optional.of(PENNY);
    }
    if (weight == NICKEL.weight && size == NICKEL.size) {
      return Optional.of(NICKEL);
    }
    if (weight == DIME.weight && size == DIME.size) {
      return Optional.of(DIME);
    }
    if (weight == QUARTER.weight && size == QUARTER.size) {
      return Optional.of(QUARTER);
    }
    return Optional.empty();
  }
}
