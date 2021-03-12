package vendingmachine.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static vendingmachine.domain.Money.money;

public class VendingMachine {

  private final List<Coin> coinReturnTray;
  private Money balance = money(0);

  private Product product;

  public VendingMachine() {
    coinReturnTray = new ArrayList<>();
  }

  public Product getProduct() {
    return product;
  }

  public String getDisplay() {
    if (product != null) {
      return "THANK YOU";
    }
    if (getBalance().isZero()) {
      return "INSERT A COIN";
    }

    return balance.toString();
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
  public List<Coin> getCoinReturnTray() {
    return Collections.unmodifiableList(coinReturnTray);
  }

  public void insertCoin(Coin coin) {
    if (coin == Coin.PENNY) {
      coinReturnTray.add(coin);
    } else {
      balance = balance.add(coin.getMoney());
    }
  }

  public void buy(Product product) {
    this.product = product;
    balance = money(0);
  }
}
