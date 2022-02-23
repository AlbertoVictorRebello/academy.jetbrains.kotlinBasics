package machine

import java.util.*

public fun main() {
    val coffeeMachine: CoffeeMachine = CoffeeMachine(Scanner(System.`in`));

    //coffeeMachine.loadRecipe("SETUP")
    coffeeMachine.supplyForecast(coffeeMachine.loadRecipe("SOFT_LATTE"))

}

