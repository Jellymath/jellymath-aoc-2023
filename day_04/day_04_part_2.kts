import java.io.File

val input = File("input.txt").readLines()

val games = input.map {
    val (winningStr, yoursStr) = it.substringAfter(": ").split(" | ")
    val winning = winningStr.split(" ").filterNot(String::isBlank).toSet()
    val yours = yoursStr.split(" ").filterNot(String::isBlank).toSet()
    winning to yours
}

val winningCounts = games.map { (winning, yours) -> (yours intersect winning).size }

var sum = 0
var cards = IntArray(winningCounts.size) { 1 }
for (i in winningCounts.indices) {
    sum += cards[i]
    for (j in (i + 1)..<(i + 1 + winningCounts[i])) {
        cards[j] += cards[i]
    }
}
println(sum)