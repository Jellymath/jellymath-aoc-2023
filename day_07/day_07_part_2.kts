import java.io.File
import kotlin.comparisons.*

val input = File("input.txt").readLines()

enum class Type {
    Highest, Pair, TwoPairs, Three, FullHouse, Four, Five
}

fun handToType(hand: String): Type {
    val count = hand.groupingBy { it }.eachCount()
    val jokerCount = count.getOrDefault('J', 0)
    val handCountValues = count.values.sorted()
    return when (jokerCount) {
        4, 5 -> Type.Five
        3 -> if (2 in handCountValues) Type.Five else Type.Four
        2 -> when (handCountValues) {
            listOf(2, 3) -> Type.Five
            listOf(1, 2, 2) -> Type.Four
            else -> Type.Three
        }

        1 -> when (handCountValues) {
            listOf(1, 4) -> Type.Five
            listOf(1, 1, 3) -> Type.Four
            listOf(1, 2, 2) -> Type.FullHouse
            listOf(1, 1, 1, 2) -> Type.Three
            else -> Type.Pair
        }

        else -> when (handCountValues) {
            listOf(5) -> Type.Five
            listOf(1, 4) -> Type.Four
            listOf(2, 3) -> Type.FullHouse
            listOf(1, 1, 3) -> Type.Three
            listOf(1, 2, 2) -> Type.TwoPairs
            listOf(1, 1, 1, 2) -> Type.Pair
            else -> Type.Highest
        }
    }
}

val order = "J23456789TQKA".toList()

val parsed = input.map {
    val (hand, bidStr) = it.split(" ")
    hand to bidStr.toInt()
}

val handComparator = compareBy(
        ::handToType,
        *(0..4).map { i -> { it: String -> order.indexOf(it[i]) } }.toTypedArray()
)
val sorted = parsed.sortedWith(compareBy(handComparator) { (hand, _) -> hand })

println(sorted.mapIndexed { i, (_, bid) -> (i + 1) * bid }.sum())
