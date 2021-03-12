package vendingmachine.domain

import spock.lang.Specification

import java.util.logging.Handler

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

    def "should display 'Thank you' enough money is given"(List<Coin> coins, Product product) {

        given:
        def vendingMachine = new VendingMachine()
        coins.forEach(vendingMachine::insertCoin)

        when:
        vendingMachine.choseProduct(product)

        then:
        vendingMachine.display == "THANK YOU"
        vendingMachine.coinReturnTray == [] as List

        where:
        coins                                   | product
        [QUARTER, QUARTER, QUARTER, QUARTER]    | Product.COLA
        [DIME, DIME, QUARTER, QUARTER, QUARTER] | Product.CHIPS
        [DIME, DIME, QUARTER, QUARTER, QUARTER] | Product.CANDY
    }

    def "should display 'INSERT COINS' when checked second time after successfull operation"() {

        given:
        def vendingMachine = new VendingMachine()
        vendingMachine.insertCoin(QUARTER)
        vendingMachine.insertCoin(QUARTER)
        vendingMachine.insertCoin(QUARTER)
        vendingMachine.insertCoin(QUARTER)
        vendingMachine.insertCoin(QUARTER)
        vendingMachine.insertCoin(QUARTER)
        when:
        vendingMachine.choseProduct(Product.COLA)
        then:
        vendingMachine.display == "THANK YOU"

        and:
        vendingMachine.display == "INSERT A COIN"
        vendingMachine.balance.value == 0.00

    }


    def "should display amount of inserted money when is't not enough for product"() {

        given:
        def vendingMachine = new VendingMachine()
        vendingMachine.insertCoin(QUARTER)

        when:
        vendingMachine.choseProduct(Product.COLA)

        then:
        vendingMachine.display == "INSERT A COIN"
        vendingMachine.balance.value == 0.25

        and:
        vendingMachine.display == "Total money: 0.25"


    }

}