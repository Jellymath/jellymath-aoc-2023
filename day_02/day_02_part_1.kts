import java.io.File

val input = File("input.txt").readLines()

val limits = mapOf("red" to 12, "green" to 13, "blue" to 14)

val parsed = input.map { line ->
    val (game, turnStrings) = line.split(": ")
    val id = game.substringAfter("Game ").toInt()
    val turns = turnStrings.split("; ", ", ").map {
        val (numStr, color) = it.split(" ")
        numStr.toInt() to color
    }
    id to turns
}

val valid = parsed.filter { (_, turns) -> turns.all { (num, color) -> num <= limits.getValue(color) } }

println(valid.sumOf { (id, _) -> id })