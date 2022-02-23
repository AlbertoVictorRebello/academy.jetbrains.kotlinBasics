package machine

import java.util.*


class CoffeeMachine {
    private val scanner: Scanner;
    private val recipeList = arrayOf<Recipe>(
        loadRecipe("SETUP"),
        loadRecipe("ESPRESSO"),
        loadRecipe("LATTE"),
        loadRecipe("CAPPUCCINO"),
        loadRecipe("SOFT_LATTE")
    )
    private val ingredientBin01: IngredientBin = IngredientBin("INGREDIENT_BIN_1")
    init {
        ingredientBin01.supply(recipeList[0])
    }

    private var moneyBin02: Int = recipeList[0].price.toInt()
    private var disposableCups: Int = recipeList[0].output

    constructor(scanner: Scanner) {
        this.scanner = scanner;
    }

    fun switchOn() {
        println("Write action (buy, fill, take, remaining, exit):")
        var action = scanner.next()

        while ("exit" != action) {
            when (action) {
                "buy" -> {
                    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")
                    val option = scanner.next()
                    if ("back" != option) {
                        val product = option.toInt()
                        if (outputForecast(recipeList[product], false, 1) > 0 && disposableCups > 0) {
                            moneyBin02 += recipeList[product].price.toInt()
                            disposableCups--
                            ingredientBin01.deploy(recipeList[product])
                        }
                    }
                }
                "fill" -> {
                    ingredientBin01.supply()
                    println("Write how many disposable cups of coffee you want to add:")
                    disposableCups += scanner.nextInt()
                }
                "take" -> {
                    System.out.printf("I gave you $%d\n\n", moneyBin02)
                    moneyBin02 = 0
                }
                "remaining" -> {
                    ingredientBin01.displayStock()
                    println(disposableCups.toString() + " disposable cups")
                    println("$$moneyBin02 of money")
                }
            }
            println("Write action (buy, fill, take, remaining, exit):")
            action = scanner.next()
        }
    }


    fun displayMessage(message: String) {
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
                recipe.addIngredient(Ingredient("coffee beans", "g", 16f))
                recipe.packingType = PackingType.CUP_OF_300_ML
                recipe.output = 1
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

    fun supplyForecast(recipe: Recipe) {
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

    fun outputForecast(recipe: Recipe = recipeList[4], verbose: Boolean = true, quantity: Int = 0): Int {
        //Scanner scanner = new Scanner(System.in);
        var demand: Int = quantity
        var output: Int = 0
        var missingIngredients = ArrayList<String>()
        var minOutput = Integer.MAX_VALUE;

        if (quantity > 0) {
            demand = quantity
        } else {
            System.out.println("Write how many cups of coffee you will need:");
            demand = scanner.nextInt();
        }

        for (itemRecipe in recipe.ingredients) {
            for (itemBin in ingredientBin01.stock) {
                if (itemRecipe.name == itemBin.name) {
                    output = (itemBin.quantity / itemRecipe.quantity).toInt()
                    minOutput = Math.min(minOutput, output)
                }
                if (demand > output) {
                    missingIngredients.add(itemBin.name)
                }
            }
        }

        if (demand == minOutput) {
            if (verbose) {
                displayMessage("Yes, I can make that amount of coffee");
                //displayMessage("I have enough resources, making you a coffee!");
            }
            return minOutput;
        } else if (demand > minOutput) {
            if (verbose) {
                displayMessage(String.format("No, I can make only %s cup(s) of coffee", minOutput));
//                for (item in missingIngredients) {
//                    System.out.printf("Sorry, not enough %s!\n", item);
//                }
            }
            return 0
        } else {
            if (verbose) {
//                displayMessage(String.format("Yes, I can make that amount of coffee (and even %s more than that)", minOutput - demand));
                displayMessage("I have enough resources, making you a coffee!");
            }
            return minOutput - demand
        }
    }
}

