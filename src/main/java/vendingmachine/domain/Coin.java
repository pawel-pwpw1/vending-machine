package vendingmachine.domain;

import java.math.BigDecimal;
import static vendingmachine.domain.Money.money;

/**
 * @author bartosz walacik
 */
public enum Coin {
    /** 1 cent coin */
    PENNY (money(0.01), 1, 1),

    /** 5 cents coin */
    NICKEL (money(0.05), 1, 5),

    /** 10 cents coin */
    DIME   (money(0.1), 2, 10),

    /** 25 cents coin */
    QUARTER(money(0.25), 3, 25),
    ;

    private final Money money;
    private final int   weight;
    private final int size;

    Coin(Money money, int weight, int size) {
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
}
