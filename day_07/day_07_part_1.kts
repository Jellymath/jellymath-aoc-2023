import java.io.File
import kotlin.comparisons.*

val input = File("input.txt").readLines()

enum class Type {
    Highest, Pair, TwoPairs, Three, FullHouse, Four, Five
}

fun handToType(hand: String): Type {
    val count = hand.groupingBy { it }.eachCount()
    return when (count.values.sorted()) {
        listOf(5) -> Type.Five
        listOf(1, 4) -> Type.Four
        listOf(2, 3) -> Type.FullHouse
        listOf(1, 1, 3) -> Type.Three
        listOf(1, 2, 2) -> Type.TwoPairs
        listOf(1, 1, 1, 2) -> Type.Pair
        else -> Type.Highest
    }
}

val order = "23456789TJQKA".toList()

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
