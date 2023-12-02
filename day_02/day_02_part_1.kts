import java.io.File

val input = File("input.txt").readLines()

val limits = mapOf("red" to 12, "green" to 13, "blue" to 14)

val parsed = input.map {
    val (game, turns) = it.split(": ")
    game.substringAfter("Game ").toInt() to turns.split("; ").map { it.split(", ") }
}

val valid = parsed.filter { (_, turns) ->
    turns.all { turn ->
        turn.all {
            val (numStr, color) = it.split(" ")
            numStr.toInt() <= limits.getValue(color)
        }
    }
}

println(valid.sumOf { it.first })