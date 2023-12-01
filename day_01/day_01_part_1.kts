import java.io.File

val input = File("input.txt").readLines()
val result = input.sumOf {
    val first = it.first(Char::isDigit)
    val last = it.last(Char::isDigit)
    "$first$last".toInt()
}
println(result)