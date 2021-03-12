package vendingmachine.domain;

import static vendingmachine.domain.Money.money;
import static vendingmachine.domain.ValidCoin.DIME;
import static vendingmachine.domain.ValidCoin.NICKEL;
import static vendingmachine.domain.ValidCoin.QUARTER;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class VendingMachine {

  private Money balance;
  private final Set<Coin> coinReturnTray;
  private String display;
  private List<Product> products;

  public VendingMachine() {
    this(money(0));
  }

  public VendingMachine(Money money) {
    coinReturnTray = new HashSet<>();
    balance = money;
    display = "INSERT A COIN";
    products = new ArrayList<>();
  }

  public String getDisplay() {
    try {
      return this.display;
    } finally {
      if (display.equals("THANK YOU")) {
        display = "INSERT A COIN";
      } else if (display.startsWith("PRICE")) {
        if (this.balance.isZero()) {
          display = "INSERT A COIN";
        } else {
          display = "BALANCE " + this.balance.toString();
        }
      }
    }
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

  public List<Product> getBoughtProducts() {
    return Collections.unmodifiableList(products);
  }

  public List<Product> pickUpProducts() {
    List<Product> productsToPickUp = List.copyOf(products);
    products = new ArrayList<>();
    return Collections.unmodifiableList(productsToPickUp);
  }

  public void insertCoin(Coin coin) {
    if (isAcceptedCoin(coin)) {
      balance = balance.add(getMoneyFor(coin).orElseThrow());
      this.display = "BALANCE " + balance;
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

  public void buy(Product product) {
    Money subtract = balance.subtract(product.getPrice());
    if (subtract.getValue().compareTo(BigDecimal.ZERO) >= 0) {
      balance = subtract;
      display = "THANK YOU";
      products.add(product);
    } else {
      display = "PRICE " + product.getPrice().toString();
    }
  }
}
