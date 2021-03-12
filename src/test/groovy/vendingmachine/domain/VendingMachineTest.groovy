package vendingmachine.domain

import spock.lang.Specification

import static vendingmachine.domain.Coin.*

class VendingMachineTest extends Specification {
    private static final String STARING_DISPLAY = "INSERT A COIN"
    def vendingMachine

    void setup() {
        vendingMachine = new VendingMachine()
    }

    def "should display 'insert a coin' when ready"() {
        when:
        vendingMachine = new VendingMachine()
        then:
        assertVendingMachine(STARING_DISPLAY, "0", [])
    }

    def "should reject penny"() {
        given:
        Coin penny = PENNY
        when:
        vendingMachine.insertCoin(penny)
        then:
        assertVendingMachine(STARING_DISPLAY, "0", [penny])
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

    private void assertVendingMachine(String balance, List<Coin> returnedCoins) {
        assertVendingMachine( balance, balance, returnedCoins)
    }

    private void assertVendingMachine(String display, String balance, List<Coin> returnedCoins) {
        vendingMachine.display == display
        vendingMachine.balance.value == new BigDecimal(balance)
        vendingMachine.coinReturnTray == returnedCoins
    }
}