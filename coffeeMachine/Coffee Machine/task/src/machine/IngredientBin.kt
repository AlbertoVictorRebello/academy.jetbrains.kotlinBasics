package machine

import java.util.*
import kotlin.collections.ArrayList

internal class IngredientBin(private val name: String) {
    private val scanner: Scanner = Scanner(System.`in`)
    var stock = ArrayList<Ingredient>()

    fun displayStock() {
        println("The coffee machine has:")
        for (item in stock) {
            System.out.printf(
                "%d %s of %s\n",
                Math.round(item.quantity),
                item.unitOfMeasurement,
                item.name            )
        }
    }

    fun supply(recipe: Recipe): Boolean {
        var input: Float
        for (item in recipe.ingredients) {
            val ingredient = Ingredient()
            ingredient.name =item.name
            ingredient.unitOfMeasurement = item.unitOfMeasurement
            System.out.printf("Write how many %s of %s the coffee machine has:", item.unitOfMeasurement, item.name)
            input = scanner.nextFloat();
            input = item.quantity
            ingredient.quantity = input
            stock.add(ingredient)
        }
        return true
    }

    fun supply(): Boolean {
        var input: Float
        for (item in stock) {
            System.out.printf("Write how many %s of %s you want to add:", item.unitOfMeasurement, item.name)
            input = scanner.nextInt().toFloat()
            item.quantity = item.quantity + input
        }
        return true
    }

    fun deploy(recipe: Recipe): Boolean {
        var input: Float
        for (item in recipe.ingredients) {
            for (ingredient in stock) {
                if (ingredient.name == item.name) {
                    ingredient.quantity = (ingredient.quantity - item.quantity)
                }
            }
        }
        return true
    }
}
