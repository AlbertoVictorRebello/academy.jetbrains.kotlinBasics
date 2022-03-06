package minesweeper

import kotlin.random.Random

class Minefield(
    private val rows: Int = 9,
    private val columns: Int = 9,
    private val numberOfMines: Int = 10) {

    companion object {
        const val MARKED_SYMBOL = '.'
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
    private fun checkMinesAround(elementRow: Int, elementColumn: Int): Char {
        if (minefield[elementRow][elementColumn] == MINE_SYMBOL) return MARKED_SYMBOL

        val baseRow = if (elementRow - 1 >= 0) elementRow - 1 else 0
        val baseColumn = if (elementColumn - 1 >= 0) elementColumn - 1 else 0
        val topRow = if (elementRow + 1 <= minefield.lastIndex) elementRow + 1 else minefield.lastIndex
        val topColumn = if (elementColumn + 1 <= minefield[minefield.lastIndex].lastIndex) elementColumn + 1 else minefield[minefield.lastIndex].lastIndex

        var counter: Int = 0

        for (row in baseRow..topRow) {
            for (column in baseColumn..topColumn) {
                if (!(elementRow == row && elementColumn == column)) {
                    if (minefield[row][column] == MINE_SYMBOL) counter++
                }
            }
        }

        return if (counter > 0) counter.toString().first() else MARKED_SYMBOL
    }

    // This function checks a given cell is not a number and so toggles marked - unmarked symbol.
    // If the cell is a 'mine' the settedMines variable is updated. It may return the minefield information or a message.
    @Deprecated("This function is supered")
    fun checkCell(row: Int, column: Int): String {
        return if (minefieldCopy[row - 1][column - 1].isDigit()) {
            "There is a number here!"
        } else if (minefield[row - 1][column - 1] == MINE_SYMBOL) {
            if (minefieldCopy[row - 1][column - 1] == MARKED_SYMBOL) {
                minefieldCopy[row - 1][column - 1] = UNMARKED_SYMBOL
                settedMines--
            } else {
                minefieldCopy[row - 1][column - 1] = MARKED_SYMBOL
                settedMines++
            }
            printMinefield(true, false)
        } else {
            if (minefieldCopy[row - 1][column - 1] == UNMARKED_SYMBOL) {
                minefieldCopy[row - 1][column - 1] = MARKED_SYMBOL
            } else {
                minefieldCopy[row - 1][column - 1] = UNMARKED_SYMBOL
            }
            printMinefield(true, false)
        }
    }

    // This function checks recursively if a given cell is not a number and so changes its symbol in acordance with the setted action.
    // If the cell is a 'mine' the settedMines variable is updated. It may return the minefield information or a message.
    fun checkCell(row: Int, column: Int, action: String = ""): Int {
        if ("" != action) this.action = action


        this.action = ""
        return 0
    }

    fun checkCell(symbol: Char): Int {
        return 0

    }

    fun printMinefield(numberView: Boolean = false, resetNumberView: Boolean = false): String {
        if (numberView) {
            if (resetNumberView) {
                for (row in 0 until minefield.size) {
                    for (column in 0 until minefield[row].size) {
                        minefieldCopy[row][column] = checkMinesAround(row, column)
                    }
                }
            }

            var rowCounter: Int = 0
            var columnCounter: Int = 0
            var grid: String = ""

            // Grid variable holds the frame with coordinates into which the minefield will be inserted into
            grid = " |"
            repeat(columns) { grid +=  (++columnCounter).toString() }
            grid += "|\n"
            grid += "-|" + "-".repeat(columns) + "|\n"

            // Here the minefield content is inserted into the frame
            grid += minefieldCopy.map {(++rowCounter).toString() + "|" + it.joinToString ( "" )}.joinToString("|\n") + "|"

            grid += "\n-|" + "-".repeat(columns) + "|"

            return grid

        } else {
            return minefield.map { it.joinToString("") }.joinToString("\n")
        }
    }
}