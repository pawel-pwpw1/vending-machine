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
        vendingMachine.coinReturnTray == []
    }

    def "should reject penny"() {
        given:
        Coin penny = PENNY
        def vendingMachine = new VendingMachine()

        when:
        vendingMachine.insertCoin(penny)

        then:
        vendingMachine.display == "INSERT A COIN"
        vendingMachine.balance.value == 0
        vendingMachine.coinReturnTray == [penny]
    }

    def "should accept #validCoin"() {
        given:
        def vendingMachine = new VendingMachine()

        when:
        vendingMachine.insertCoin(validCoin)

        then:
        vendingMachine.display == balance
        vendingMachine.balance.value == new BigDecimal(balance)
        vendingMachine.coinReturnTray == []

        where:
        validCoin | balance
        NICKEL    | "0.05"
        DIME      | "0.10"
        QUARTER   | "0.25"
    }

    def "should return sum of coins and invalid coins"() {
        given:
        def vendingMachine = new VendingMachine()

        when:
        coins.forEach(vendingMachine::insertCoin)

        then:
        vendingMachine.display == balance
        vendingMachine.balance.value == new BigDecimal(balance)
        vendingMachine.coinReturnTray == returnedCoins

        where:
        coins                                  | balance | returnedCoins
        [PENNY, NICKEL, PENNY, DIME]           | "0.15"  | [PENNY, PENNY]
        [QUARTER, NICKEL, NICKEL, PENNY, DIME] | "0.45"  | [PENNY]
    }
}