import java.io.File

val input = File("input.txt").readLines()

val games = input.map {
    val (winningStr, yoursStr) = it.substringAfter(": ").split(" | ")
    val winning = winningStr.split(" ").filterNot(String::isBlank).toSet()
    val yours = yoursStr.split(" ").filterNot(String::isBlank).toSet()
    winning to yours
}

val winningCounts = games.map { (winning, yours) -> (yours intersect winning).size }

println(winningCounts.sumOf { if (it == 0) 0 else 1 shl (it - 1) })
