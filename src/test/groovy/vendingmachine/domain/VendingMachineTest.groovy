package vendingmachine.domain

import spock.lang.Specification

import static vendingmachine.domain.Coin.*

class VendingMachineTest extends Specification {

    def "should display 'insert a coin' when ready"() {
        when:
        def vendingMachine = new VendingMachine()

        then:
        vendingMachine.display == "INSERT A COIN"
        vendingMachine.balance.value == 0
        vendingMachine.coinReturnTray == [] as List
    }

    def "should accept valid coins "(List<Coin> coins, BigDecimal balance, rejected, String display) {
        when:
        def vendingMachine = new VendingMachine()
        for (Coin c in coins) {
            vendingMachine.insertCoin(c)
        }
        then:
        vendingMachine.display == display
        vendingMachine.balance.value == balance
        vendingMachine.coinReturnTray.size() == rejected

        where:
        coins                 | balance | rejected | display
        [PENNY, NICKEL, DIME] | 0.15    | 1        | 'Total money: 0.15'
        [PENNY, PENNY, PENNY] | 0.00    | 3        | 'INSERT A COIN'
        [QUARTER]             | 0.25    | 0        | 'Total money: 0.25'
    }


}