package vendingmachine.domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static vendingmachine.domain.Money.money;

public class VendingMachine {

    private List<Coin> coinReturnTray;
    private Money balance = Money.money(0);

    public VendingMachine() {
        coinReturnTray = new ArrayList<>();
    }

    public String getDisplay() {
        if (getBalance().isZero()) {
            return "INSERT A COIN";
        }

        return "Total money: "+balance.toString();
    }

    public void insertCoin(Coin coin){
        if(coin == Coin.PENNY){
            coinReturnTray.add(coin);
            return;
        }
        this.balance = balance.add(coin.getMoney());
    }

    /**
     * Current amount on display:
     * sum of *valid* coins inserted, minus sold products, minus change
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
