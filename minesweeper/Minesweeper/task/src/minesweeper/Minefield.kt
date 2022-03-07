package minesweeper

import kotlin.random.Random

class Minefield(
    private val rows: Int = 9,
    private val columns: Int = 9,
    private val numberOfMines: Int = 10) {

    companion object {
        const val MARKED_SYMBOL = '.'
        const val FREE_SYMBOL = '/'
        const val MINE_SYMBOL = 'X'
        const val UNMARKED_SYMBOL = '\u002A'
    }

    internal var settedMines = 0
    private var action = ""
    private var calculatedFactor = numberOfMines.toDouble() / (rows.toDouble() * columns.toDouble())
    private val minefield = MutableList<MutableList<Char>>(rows) {
        MutableList<Char>(columns) { setCell(calculatedFactor) }}
    private val minefieldCopy = MutableList<MutableList<Char>>(rows) {
        MutableList<Char>(columns) { MARKED_SYMBOL }}

    init {
        setCell(numberOfMines - settedMines)
    }

    // This function defines if the sell is 'safe' or is a 'mine'
    private fun setCell(factor: Double): Char {
        val random = Random.Default
        return if (random.nextDouble() <= factor && settedMines < numberOfMines) {
            settedMines++
            MINE_SYMBOL
        } else MARKED_SYMBOL
    }

    // This function sets the total 'mines' quantity defined by the player
    private fun setCell(mineUnbalance: Int): Int {
        val random = Random.Default
        var counter = mineUnbalance
        val replaceSymbol = if (counter > 0) MINE_SYMBOL else MARKED_SYMBOL
        while (counter != 0) {
            for (row in 0 until rows) {
                for (column in 0 until columns) {
                    if (minefield[row][column] != replaceSymbol) {
                        minefield[row][column] = setCell(factor = calculatedFactor)
                        counter -= if (minefield[row][column] == replaceSymbol) 1 else 0
                    }
                }
            }
        }
        return counter
    }

    // This function identifies the number of mines around a cell. Return as Char to be added to the minefield mapping object
    private fun checkMinesAround(elementRow: Int, elementColumn: Int) {
        val skipThisCell: Boolean = (minefieldCopy[elementRow][elementColumn] == FREE_SYMBOL) ||
                minefieldCopy[elementRow][elementColumn].isDigit()

        if (!skipThisCell) {
            val baseRow = if (elementRow - 1 >= 0) elementRow - 1 else 0
            val baseColumn = if (elementColumn - 1 >= 0) elementColumn - 1 else 0
            val topRow = if (elementRow + 1 <= minefield.lastIndex) elementRow + 1 else minefield.lastIndex
            val topColumn =
                if (elementColumn + 1 <= minefield[minefield.lastIndex].lastIndex) elementColumn + 1 else minefield[minefield.lastIndex].lastIndex

            var counter: Int = 0

            for (row in baseRow..topRow) {
                for (column in baseColumn..topColumn) {
                    if (!(elementRow == row && elementColumn == column)) {
                        if (minefield[row][column] == MINE_SYMBOL) counter++
                    }
                }
            }

            if (counter == 0) {
                minefieldCopy[elementRow][elementColumn] = FREE_SYMBOL
                for (row in baseRow..topRow) {
                    for (column in baseColumn..topColumn) {
                        if (!(elementRow == row && elementColumn == column)) {
                            checkMinesAround(row, column)
                        }
                    }
                }
            } else {
                minefieldCopy[elementRow][elementColumn] = (counter + 48).toChar()
            }
        }
    }



    // This function checks recursively if a given cell is not a number and so changes its symbol in acordance with the setted action.
    // If the cell is a 'mine' the settedMines variable is updated. It may return the minefield information or a message.
    fun checkCell(gridRow: Int, gridColumn: Int, action: String): String {
        val row = gridRow - 1
        val column = gridColumn - 1

        val message: String = if (minefieldCopy[row][column].isDigit()) {
            // "There is a number here!"
            ""
        } else if ("free" == action) {
            if (minefield[row][column] == MINE_SYMBOL) {
                for (row in 0 until rows) {
                    for (column in 0 until columns) {
                        if (minefield[row][column] == MINE_SYMBOL) minefieldCopy[row][column] = MINE_SYMBOL
                    }
                }
                "computer"
            } else {
                checkMinesAround(row, column)
                ""
            }

        } else if ("mine" == action) {
            if (minefield[row][column] == MINE_SYMBOL) {
                if (minefieldCopy[row][column] == MARKED_SYMBOL) {
                    minefieldCopy[row][column] = UNMARKED_SYMBOL
                    settedMines--
                } else {
                    minefieldCopy[row][column] = MARKED_SYMBOL
                    settedMines++
                }

            } else {
                if (minefieldCopy[row][column] == MARKED_SYMBOL) {
                    minefieldCopy[row][column] = UNMARKED_SYMBOL
                } else {
                    minefieldCopy[row][column] = MARKED_SYMBOL
                }
            }

            ""
        } else {
            "OPS!"
        }

        this.action = ""
        return message

    }

    // This function counts how many symbols of a specific Char the minefieldCopy variable contains.
    fun checkCell(symbol: Char): Int {
        var counter = 0

        for (row in 0 until rows) {
            for (column in 0 until columns) {
                if (minefieldCopy[row][column] == symbol) {
                   counter++
                }
            }
        }
        return counter
    }

    fun printMinefield(printMinefieldCopy: Boolean = true): String {
        var grid: String = ""
        var content: String = ""
        var rowCounter: Int = 0
        var columnCounter: Int = 0

        // Grid variable holds the frame with coordinates into which the minefield will be inserted into
        grid = " |"
        repeat(columns) { grid +=  (++columnCounter).toString() }
        grid += "|\n"
        grid += "-|" + "-".repeat(columns) + "|\n"

        // Here the minefield content is inserted into the grid
        if (printMinefieldCopy) {
            content  = minefieldCopy.map {(++rowCounter).toString() + "|" + it.joinToString ( "" )}.joinToString("|\n") + "|"
        } else {
            content  = minefield.map {(++rowCounter).toString() + "|" + it.joinToString ( "" )}.joinToString("|\n") + "|"
        }

        grid += content

        grid += "\n-|" + "-".repeat(columns) + "|"

        return grid
    }
}