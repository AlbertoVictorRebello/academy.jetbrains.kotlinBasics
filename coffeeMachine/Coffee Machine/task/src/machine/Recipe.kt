package machine


class Recipe() {
    internal var name: String?
    internal var preparationTime: Int
    internal var ingredients: ArrayList<Ingredient>
    internal var packingType: PackingType?
    internal var output: Int
    internal var price: Float

    init {
        name = ""
        preparationTime = 0
        ingredients = arrayListOf<Ingredient>()
        packingType = null
        output = 0
        price = 0F
    }

    fun addIngredient(ingredient: Ingredient){
        ingredients!!.add(ingredient)
    }

    /*

    fun setName (name: String) {
        this.name = name
    }

    fun setPreparationTime(preparationTime: Int): Unit {
        this.preparationTime = preparationTime
    }

    fun setIngredients(ingredients: ArrayList<Ingredient>) {
        this.ingredients = ingredients
    }

    fun setPackingType(packingType: PackingType?) {
        this.packingType = packingType!!
    }

    fun setPrice(price: Float) {
        this.price = price
    }

    fun getPrice(): Float {
        return price
    }

    fun getName(): String? {
        return this.name
    }

    fun getPreparationTime(): Int {
        return this.preparationTime
    }

    fun getIngredients(): ArrayList<Ingredient?>? {
        return this.ingredients
    }

    fun getPackingType(): PackingType? {
        return this.packingType
    }

    fun getYield(): Int {
        return this.output
    }

    fun setYield(yield: Int) {
        this.output = yield
    }

     */
}