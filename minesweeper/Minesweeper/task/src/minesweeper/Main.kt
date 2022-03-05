package minesweeper

private fun main() {
    // Stage 2
    println("How many mines do you want on the field?")
    val numberOfMines = readLine()!!.toInt()
    val minefield = Minefield(numberOfMines = numberOfMines)
    print(minefield.printMinefield())
}

