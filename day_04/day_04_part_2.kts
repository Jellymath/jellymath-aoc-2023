import java.io.File

val input = File("input.txt").readLines()

val games = input.map {
    val (winningStr, yoursStr) = it.substringAfter(": ").split(" | ")
    val winning = winningStr.split(" ").filterNot(String::isBlank).toSet()
    val yours = yoursStr.split(" ").filterNot(String::isBlank).toSet()
    winning to yours
}

val winningCounts = games.map { (winning, yours) -> (yours intersect winning).size }

fun <T> List<T>.updateFirst(n: Int, transform: (T) -> T) = take(n).map(transform) + drop(n)

val (sum, _) = winningCounts.fold(0 to List(winningCounts.size) { 1 }) { (currentSum, cards), count ->
    val cardsCount = cards.first()
    (currentSum + cardsCount) to cards.drop(1).updateFirst(count) { it + cardsCount }
}

println(sum)