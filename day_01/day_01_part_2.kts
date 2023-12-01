import java.io.File

val input = File("input.txt").readLines()

val digits = ('0'..'9').associateBy { it.toString() }
val words = mapOf(
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9'
)

val mappings = digits + words

val regex = Regex(mappings.keys.joinToString("|", "(?=(", "))."))
val result = input.sumOf {
    val matches = regex.findAll(it).toList()
    val first = mappings.getValue(matches.first().groupValues[1])
    val last = mappings.getValue(matches.last().groupValues[1])
    "$first$last".toInt()
}
println(result)