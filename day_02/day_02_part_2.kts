import java.io.File

val input = File("input.txt").readLines()

val parsed = input.map { line ->
    val (_, turns) = line.split(": ")
    turns.split("; ", ", ").map {
        val (numStr, color) = it.split(" ")
        numStr.toInt() to color
    }
}
val maxByColor = parsed.map {
    it.groupingBy { (_, color) -> color }.fold(0) { acc, (num, _) -> maxOf(acc, num) }
}

println(maxByColor.sumOf { it.values.reduce(Int::times) })