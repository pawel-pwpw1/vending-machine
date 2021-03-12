package vendingmachine.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static vendingmachine.domain.Money.money;

public class VendingMachine {

  private List<Coin> coinReturnTray;
  private Money balance = Money.money(0);
  private String display;
  private String oneTimeDisplay;
  private boolean bought = false;
  private boolean productChosen = false;

  public VendingMachine() {
    coinReturnTray = new ArrayList<>();
  }

  public String getDisplay() {
    if (getBalance().isZero()) {
      return "INSERT A COIN";
    }

    if(productChosen){
      productChosen = false;
      if (bought) {
        balance = money(0);
        bought = false;
      }
      return oneTimeDisplay;
    }

    return display;
  }


  public void choseProduct(Product product) {
    productChosen = true;
    if (product.getPrice().getValue().compareTo(balance.getValue()) <= 0) {
      oneTimeDisplay = "THANK YOU";
      bought = true;
    } else {
      oneTimeDisplay = "INSERT A COIN";
    }
  }

  public void insertCoin(Coin coin) {
    if (coin == Coin.PENNY) {
      coinReturnTray.add(coin);
      return;
    }
    this.balance = balance.add(coin.getMoney());
    display = "Total money: " + balance.toString();
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
}
