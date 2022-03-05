package minesweeper

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

    fun printMinefield(): String {
        return minefield.map { it.joinToString("") }.joinToString("\n")
    }
}