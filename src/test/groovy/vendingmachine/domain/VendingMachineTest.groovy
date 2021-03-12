package vendingmachine.domain

import spock.lang.Specification
import spock.lang.Unroll

import static vendingmachine.domain.Product.*

class VendingMachineTest extends Specification {

    def "should display 'insert a coin' when ready"() {
        when:
        def vendingMachine = new VendingMachine()

        then:
        vendingMachine.display == "INSERT A COIN"
        vendingMachine.balance.value == 0
        vendingMachine.coinReturnTray == [] as Set
    }

    def 'should reject pennies'() {
        given:
        def vendingMachine = new VendingMachine()
        def coin = new Coin(1, 1);
        when:
        vendingMachine.insertCoin(coin)
        then:
        vendingMachine.balance.value == 0
        vendingMachine.coinReturnTray == [coin] as Set
    }

    @Unroll
    def 'should accept coin with size: #size and weight: #weight'(weight, size, balance) {
        given:
        def vendingMachine = new VendingMachine()
        def coin = new Coin(weight, size);
        when:
        vendingMachine.insertCoin(coin)
        then:
        vendingMachine.balance.value == balance
        vendingMachine.coinReturnTray == [] as Set
        vendingMachine.display == 'BALANCE ' + balance.toString()


        where:
        weight | size | balance
        1      | 5    | 0.05
        2      | 10   | 0.10
        3      | 25   | 0.25
    }

    @Unroll
    def 'should buy #product without change'(product) {
        given:
        def vendingMachine = new VendingMachine(product.price)

        when:
        vendingMachine.buy(product)

        then:
        vendingMachine.balance.value == 0
        vendingMachine.display == 'THANK YOU'
        vendingMachine.coinReturnTray == [] as Set
        vendingMachine.display == 'INSERT A COIN'

        where:
        product << [COLA, CHIPS, CANDY]
    }

    def 'should display PRICE if no coin was inserted'() {
        given:
        def product = COLA;
        def vendingMachine = new VendingMachine()

        when:
        vendingMachine.buy(product)

        then:
        vendingMachine.balance.value == 0
        vendingMachine.display == 'PRICE ' + product.price.toString()
        vendingMachine.coinReturnTray == [] as Set
        vendingMachine.display == 'INSERT A COIN'
    }
}
