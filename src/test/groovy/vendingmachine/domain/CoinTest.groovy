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
}
