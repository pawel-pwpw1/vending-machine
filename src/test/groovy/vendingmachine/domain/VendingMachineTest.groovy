package vendingmachine.domain

import spock.lang.Specification

import static vendingmachine.domain.Coin.*
import static vendingmachine.domain.Product.CANDY
import static vendingmachine.domain.Product.CHIPS
import static vendingmachine.domain.Product.COLA

class VendingMachineTest extends Specification {
    private static final String STARTING_DISPLAY = "INSERT A COIN"
    VendingMachine vendingMachine

    void setup() {
        vendingMachine = new VendingMachine()
    }

    def "should display 'insert a coin' when ready"() {
        when:
        vendingMachine = new VendingMachine()
        then:
        assertVendingMachine(STARTING_DISPLAY, "0", [])
    }

    def "should reject penny"() {
        given:
        Coin penny = PENNY
        when:
        vendingMachine.insertCoin(penny)
        then:
        assertVendingMachine(STARTING_DISPLAY, "0", [penny])
    }

    def "should accept #validCoin"(Coin validCoin, String balance) {
        when:
        vendingMachine.insertCoin(validCoin)
        then:
        assertVendingMachine(balance, [])

        where:
        validCoin | balance
        NICKEL    | "0.05"
        DIME      | "0.10"
        QUARTER   | "0.25"
    }

    def "should return sum of coins and invalid coins"() {
        when:
        coins.forEach(vendingMachine::insertCoin)
        then:
        assertVendingMachine(balance, returnedCoins)
        where:
        coins                                  | balance | returnedCoins
        [PENNY, NICKEL, PENNY, DIME]           | "0.15"  | [PENNY, PENNY]
        [QUARTER, NICKEL, NICKEL, PENNY, DIME] | "0.45"  | [PENNY]
    }

    def "should return product"() {
        given:
        coins.forEach(vendingMachine::insertCoin)
        when:
        vendingMachine.buy(product)
        then:
        vendingMachine.display == "THANK YOU"
        vendingMachine.balance.value == 0
        vendingMachine.product == product
        vendingMachine.display == STARTING_DISPLAY

        where:
        coins                                | product
        [QUARTER, QUARTER, QUARTER, QUARTER] | COLA
        [NICKEL, QUARTER, QUARTER, QUARTER]  | CANDY
        [NICKEL, DIME, QUARTER, QUARTER]     | CHIPS
    }

    def "should not return product when not enough balance"() {
        given:
        coins.forEach(vendingMachine::insertCoin)
        when:
        vendingMachine.buy(product)
        then:
        vendingMachine.display == toDisplayFirstTime
        vendingMachine.product == null
        vendingMachine.display == toDisplayNext

        where:
        coins    | product | toDisplayFirstTime | toDisplayNext
        []       | COLA    | "1.00"             | STARTING_DISPLAY
        [NICKEL] | CANDY   | "0.65"             | "0.05"
    }

    private void assertVendingMachine(String balance, List<Coin> returnedCoins) {
        assertVendingMachine(balance, balance, returnedCoins)
    }

    private void assertVendingMachine(String display, String balance, List<Coin> returnedCoins) {
        vendingMachine.display == display
        vendingMachine.balance.value == new BigDecimal(balance)
        vendingMachine.coinReturnTray == returnedCoins
    }
}