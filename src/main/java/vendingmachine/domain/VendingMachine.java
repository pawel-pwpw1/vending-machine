package vendingmachine.domain;

import static vendingmachine.domain.Money.money;
import static vendingmachine.domain.ValidCoin.DIME;
import static vendingmachine.domain.ValidCoin.NICKEL;
import static vendingmachine.domain.ValidCoin.QUARTER;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class VendingMachine {

  private Money balance;
  private final Set<Coin> coinReturnTray;

  public VendingMachine() {
    coinReturnTray = new HashSet<>();
    balance = money(0);
  }

  public String getDisplay() {
    if (getBalance().isZero()) {
      return "INSERT A COIN";
    }

    return "BALANCE " + getBalance().toString();
  }

  /**
   * Current amount on display: sum of *valid* coins inserted, minus sold products, minus change
   */
  public Money getBalance() {
    return balance;
  }

  /**
   * @return unmodifiableSet
   */
  public Set<Coin> getCoinReturnTray() {
    return Collections.unmodifiableSet(coinReturnTray);
  }

  public void insertCoin(Coin coin) {
    if (isAcceptedCoin(coin)) {
      balance = balance.add(getMoneyFor(coin).orElseThrow());
    } else {
      coinReturnTray.add(coin);
    }
  }

  private boolean isAcceptedCoin(Coin coin) {
    return isValidCoin(coin, NICKEL) || isValidCoin(coin, DIME) || isValidCoin(coin, QUARTER);
  }

  private boolean isValidCoin(Coin coin, ValidCoin validCoin) {
    return coin.getWeight() == validCoin.getWeight() && coin.getSize() == validCoin.getSize();
  }

  private Optional<Money> getMoneyFor(Coin coin) {
    return ValidCoin.getFor(coin.getWeight(), coin.getSize()).map(ValidCoin::getMoney);
  }
}
