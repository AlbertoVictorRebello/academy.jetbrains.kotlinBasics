package machine

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val coffeeMachine: CoffeeMachine = CoffeeMachine()
    var option: String

    println("Write action (buy, fill, take, remaining, exit):")
    var action = scanner.next()
    var response: String = String(action.toCharArray())
    while ("exit" != action) {
        println("Write action (buy, fill, take, remaining, exit):")
        when (action) {
            "buy" -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
                option = scanner.next()
                if ("back" != option) {
                    response =  coffeeMachine.switchOn(action,option)
                } else {
                    response = action
                }
            }
            "fill" -> {
                coffeeMachine.switchOn(action)
                println("Write how many disposable cups of coffee you want to add:")
                option = scanner.next()
                response = coffeeMachine.switchOn(action, option)
            }
            "take" -> {
                response = coffeeMachine.switchOn(action)
            }
            "remaining" -> {
                response = coffeeMachine.switchOn(action)
            }
        }
        if (action != response) {
            println(response)
        }
        println("Write action (buy, fill, take, remaining, exit):")
        action = scanner.next()
    }
}

