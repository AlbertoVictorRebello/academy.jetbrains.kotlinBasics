package minesweeper

private fun main() {
    // Stage 4
    println("How many mines do you want on the field?")
    val numberOfMines = readLine()!!.toInt()
    val minefield = Minefield(numberOfMines = numberOfMines)
    println(minefield.printMinefield(true, true))

    while (minefield.settedMines > 0) {
        println("Set/delete mine marks (x and y coordinates):")
        val (y, x) = readLine()!!.split(" ").map { it.toInt() }
        println(minefield.checkCell(x, y))
    }
    print("Congratulations! You found all the mines!")


}

