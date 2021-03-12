package vendingmachine.domain

import spock.lang.Specification

class VendingMachineTest extends Specification {

    def "should display 'insert a coin' when ready"() {
        when:
        def vendingMachine = new VendingMachine()

        then:
        vendingMachine.display == "INSERT A COIN"
        vendingMachine.balance.value == 0
        vendingMachine.coinReturnTray == [] as Set
    }

    def "should reject pennies"() {
        given:
        def vendingMachine = new VendingMachine()
        when:
        vendingMachine.insertCoins(1,1)
        then:
        vendingMachine.balance.value == 0
        vendingMachine.coinReturnTray == [Coin.PENNY] as Set
    }
}
