package machine

class Ingredient(
    internal var name: String,
    internal var unitOfMeasurement: String,
    internal var quantity: Float = 0.0f
) {

    constructor() : this("","",0f)
}