package minesweeper

import kotlin.random.Random

class Minefield(
    private val rows: Int = 9,
    private val columns: Int = 9) {
    private val minefield = MutableList<MutableList<Char>>(rows) {
        MutableList<Char>(columns) { setCell() }}

    fun printMinefield(): String {
        return minefield.map { it.joinToString("") }.joinToString("\n")
    }

    fun setCell(): Char {
        val random = Random.Default
        return if (random.nextDouble() <=0.2) 'X' else '.'
    }

}