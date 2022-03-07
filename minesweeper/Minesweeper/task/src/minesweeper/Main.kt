package minesweeper

private fun main() {
    // Stage 5
    println("How many mines do you want on the field?")
    val numberOfMines = readLine()!!.toInt()
    val minefield = Minefield(numberOfMines = numberOfMines)
    var theWinnerIs = ""

    println(minefield.printMinefield(true))


    while (theWinnerIs == "") {
        println("Set/unset mine marks or claim a cell as free:")
        val (inputY, inputX, action) = readLine()!!.split(" ")
        val x = inputX.toInt()
        val y = inputY.toInt()

        theWinnerIs = minefield.checkCell(x, y, action)
        println(minefield.printMinefield())

        if (minefield.settedMines == 0) theWinnerIs = "player"
        if (minefield.checkCell(Minefield.MARKED_SYMBOL) == 0) theWinnerIs = "player"
    }
    if ("player" == theWinnerIs) {
        print("Congratulations! You found all the mines!")
    } else if ("computer" == theWinnerIs) {
        print("You stepped on a mine and failed!")
    } else {
        print("Ops!")
    }

}

