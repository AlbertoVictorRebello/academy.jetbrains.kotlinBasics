package machine

class Ingredient(name: String, unitOfMeasurement: String, quantity: Float ) {
    internal var name: String = name
    internal var unitOfMeasurement: String = unitOfMeasurement
    internal var quantity: Float = quantity

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name!!
    }

    fun getUnitOfMeasurement(): String {
        return unitOfMeasurement
    }

    fun setUnitOfMeasurement(unitOfMeasurement: String) {
        this.unitOfMeasurement = unitOfMeasurement!!
    }

    fun getQuantity(): Float {
        return quantity
    }

    fun setQuantity(quantity: Float) {
        this.quantity = quantity
    }
}