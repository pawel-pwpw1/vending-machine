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
  private String display = "INSERT A COIN";

  public VendingMachine() {
    coinReturnTray = new ArrayList<>();
  }

  public Product getProduct() {
    return product;
  }

  private void clear() {
    balance = money(0);
    display = prepareDisplayFromBalance();
    coinReturnTray.clear();
  }

  private String prepareDisplayFromBalance() {
    if (balance.isZero()) {
      return "INSERT A COIN";
    }
    return balance.toString();
  }

  public String getDisplay() {
    if (display.equals("THANK YOU")) {
      clear();
      return "THANK YOU";
    }
    if (!display.equals(balance.toString())) {
      String toReturn = display;
      display = prepareDisplayFromBalance();
      return toReturn;
    }
    return display;
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
      display = balance.toString();
    }
  }

  public void buy(Product product) {
    if (balance.isGreaterOrEqualThan(product.getPrice())) {
      this.product = product;
      balance = money(0);
      display = "THANK YOU";
    } else {
      display = product.getPrice().toString();
    }
  }
}
