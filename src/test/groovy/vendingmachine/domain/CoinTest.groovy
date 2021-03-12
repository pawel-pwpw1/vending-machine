package vendingmachine.domain

import spock.lang.Specification
import spock.lang.Unroll

import static vendingmachine.domain.Coin.*

class CoinTest extends Specification {
    @Unroll
    def "#coin should has value #expectedValue"(Coin coin, expectedValue) {
        expect:
        coin.value == expectedValue

        where:
        coin    | expectedValue
        PENNY   | 0.01
        NICKEL  | 0.05
        DIME    | 0.1
        QUARTER | 0.25
    }

    @Unroll
    def "#coin should has size #expectedSize"(Coin coin, expectedSize) {
        expect:
        coin.size == expectedSize

        where:
        coin    | expectedSize
        PENNY   | 1
        NICKEL  | 5
        DIME    | 10
        QUARTER | 25
    }

}
