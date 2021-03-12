package vendingmachine.domain

import spock.lang.Specification

import static vendingmachine.domain.Coin.*

class VendingMachineTest extends Specification {
    def vendingMachine;

    void setup() {
        vendingMachine = new VendingMachine()
    }

    def "should display 'insert a coin' when ready"() {
        when:
        vendingMachine = new VendingMachine()
        then:
        assertVendingMachine(vendingMachine, "INSERT A COIN", "0", [])
    }

    def "should reject penny"() {
        given:
        Coin penny = PENNY
        when:
        vendingMachine.insertCoin(penny)
        then:
        assertVendingMachine(vendingMachine, "INSERT A COIN", "0", [penny])
    }

    def "should accept #validCoin"(Coin validCoin, String balance) {
        when:
        vendingMachine.insertCoin(validCoin)
        then:
        assertVendingMachine(vendingMachine, balance, [])

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
        assertVendingMachine(vendingMachine, balance, returnedCoins)
        where:
        coins                                  | balance | returnedCoins
        [PENNY, NICKEL, PENNY, DIME]           | "0.15"  | [PENNY, PENNY]
        [QUARTER, NICKEL, NICKEL, PENNY, DIME] | "0.45"  | [PENNY]
    }

    private static void assertVendingMachine(VendingMachine vendingMachine, String balance, List<Coin> returnedCoins) {
        assertVendingMachine(vendingMachine, balance, balance, returnedCoins)
    }

    private static void assertVendingMachine(VendingMachine vendingMachine, String display, String balance, List<Coin> returnedCoins) {
        vendingMachine.display == display
        vendingMachine.balance.value == new BigDecimal(balance)
        vendingMachine.coinReturnTray == returnedCoins
    }
}