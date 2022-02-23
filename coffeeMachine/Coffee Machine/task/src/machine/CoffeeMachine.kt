package machine

import java.util.*


class CoffeeMachine {
    private val scanner: Scanner;
    private val recipeList = arrayOf<Recipe>(
        loadRecipe("SETUP"),
        loadRecipe("ESPRESSO"),
        loadRecipe("LATTE"),
        loadRecipe("CAPPUCCINO")
    )
    private val ingredientBin01: IngredientBin = IngredientBin("INGREDIENT_BIN_1")
    init {
        ingredientBin01.supply(recipeList[0])
    }

    constructor(scanner: Scanner) {
        this.scanner = scanner;
    }

    fun loadRecipe(name: String): Recipe {
        val recipe = Recipe()
        when (name) {
            "SETUP" -> {
                recipe.name = "setup"
                recipe.preparationTime = 6000
                recipe.addIngredient(Ingredient("water", "ml", 400f))
                recipe.addIngredient(Ingredient("milk", "ml", 540f))
                recipe.addIngredient(Ingredient("coffee beans", "g", 120f))
                recipe.packingType = PackingType.CUP_OF_300_ML
                recipe.output = 9
                recipe.price = 550F
            }
            "ESPRESSO" -> {
                recipe.name = "espresso"
                recipe.preparationTime = 10
                recipe.addIngredient(Ingredient("water", "ml", 250f))
                recipe.addIngredient(Ingredient("milk", "ml", 0f))
                recipe.addIngredient(Ingredient("coffee beans", "g", 160f))
                recipe.packingType = PackingType.CUP_OF_300_ML
                recipe.output = 9
                recipe.price = 4F
            }
            "LATTE" -> {
                recipe.name = "latte"
                recipe.preparationTime = 15
                recipe.addIngredient(Ingredient("water", "ml", 350f))
                recipe.addIngredient(Ingredient("milk", "ml", 75f))
                recipe.addIngredient(Ingredient("coffee beans", "g", 20f))
                recipe.packingType = PackingType.CUP_OF_300_ML
                recipe.output = 1
                recipe.price = 7F
            }
            "CAPPUCCINO" -> {
                recipe.name = "cappuccino"
                recipe.preparationTime = 15
                recipe.addIngredient(Ingredient("water", "ml", 200f))
                recipe.addIngredient(Ingredient("milk", "ml", 100f))
                recipe.addIngredient(Ingredient("coffee beans", "g", 12f))
                recipe.packingType = PackingType.CUP_OF_300_ML
                recipe.output = 1
                recipe.price = 6F
            }
            "SOFT_LATTE" -> {
                recipe.name = "soft latte"
                recipe.preparationTime = 0
                recipe.addIngredient(Ingredient("water", "ml", 200f))
                recipe.addIngredient(Ingredient("milk", "ml", 50f))
                recipe.addIngredient(Ingredient("coffee beans", "g", 15f))
                recipe.packingType = PackingType.CUP_OF_300_ML
                recipe.output = 9
                recipe.price = 6F
            }
        }
        return recipe
    }

    public fun supplyForecast(recipe: Recipe) {
        println("Write how many cups of coffee you will need:");
        val demand = scanner.nextInt();
        var message = "For $demand cups of coffee you will need:\n";
        for (item in recipe.ingredients!!) {
            message += String.format("%d %s of %s\n", (item!!.quantity * demand).toInt(),
                item.unitOfMeasurement,
                item.name)
        }
        displayMessage(message);
    }

    private fun displayMessage(message: String) {
        println(
            when (message) {
            "COFFEE_PREPARATION" -> "Starting to make a coffee\n" +
                    "Grinding coffee beans\n" +
                    "Boiling water\n" +
                    "Mixing boiled water with crushed coffee beans\n" +
                    "Pouring coffee into the cup\n" +
                    "Pouring some milk into the cup\n" +
                    "Coffee is ready!";
            else -> message
        }
        );
    }
}