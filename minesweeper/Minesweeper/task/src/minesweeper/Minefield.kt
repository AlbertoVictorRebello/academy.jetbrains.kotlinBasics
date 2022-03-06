package minesweeper

import kotlin.math.min
import kotlin.random.Random

const val SAFE_SYMBOL = '.'
const val MINE_SYMBOL = 'X'

class Minefield(
    private val rows: Int = 9,
    private val columns: Int = 9,
    private val numberOfMines: Int = 10) {

    private var settedMines = 0
    private var calculatedFactor = numberOfMines.toDouble() / (rows.toDouble() * columns.toDouble())
    private val minefield = MutableList<MutableList<Char>>(rows) {
        MutableList<Char>(columns) { setCell(calculatedFactor) }}

    init {
        setCell(numberOfMines - settedMines)
    }

    // Defines if the sell is 'safe' or is a 'mine'
    private fun setCell(factor: Double): Char {
        val random = Random.Default
        return if (random.nextDouble() <= factor && settedMines < numberOfMines) {
            settedMines++
            MINE_SYMBOL
        } else SAFE_SYMBOL
    }

    private fun setCell(mineUnbalance: Int): Int {
        val random = Random.Default
        var counter = mineUnbalance
        val replaceSymbol = if (counter > 0) MINE_SYMBOL else SAFE_SYMBOL
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


    private fun checkMinesAround(elementRow: Int, elementColumn: Int): Char {
        if (minefield[elementRow][elementColumn] == MINE_SYMBOL) return MINE_SYMBOL

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

        return if (counter > 0) counter.toString().first() else SAFE_SYMBOL
    }

    fun printMinefield(numberView: Boolean = false): String {
        if (numberView) {
            val minefieldCopy = MutableList<MutableList<Char>>(rows) {
                MutableList<Char>(columns) { SAFE_SYMBOL }}
            for (row in 0 until minefield.size) {
                for (column in 0 until minefield[row].size) {
                    minefieldCopy[row][column] = checkMinesAround(row, column)
                }
            }
            return minefieldCopy.map {it.joinToString ( "" )}.joinToString("\n")

        } else {
            return minefield.map { it.joinToString("") }.joinToString("\n")
        }
    }
}