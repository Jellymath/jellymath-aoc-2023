import java.io.File

val input = File("input.txt").readLines()

val instructions = input.first()
val regex = """([A-Z0-9]{3}) = \(([A-Z0-9]{3})\, ([A-Z0-9]{3})\)""".toRegex()
val routes = input.drop(2).associate {
    val (_, from, left, right) = regex.matchEntire(it)!!.groupValues
    from to (left to right)
}

fun move(from: String, direction: Char) = routes.getValue(from)
        .let { (l, r) -> if (direction == 'L') l else r }
fun lcm(a: Long, b: Long) = generateSequence(a) { it + a }.first { it % b == 0L }

val starts = routes.keys.filter { it.endsWith("A") }

val cycles = starts.map {  start ->
    generateSequence(start to 0) { (from, i) ->
        move(from, instructions[i]) to ((i + 1) % instructions.length)
    }.takeWhile { (from, _) -> !from.endsWith("Z") }
}

println(cycles.map { it.count().toLong() }.reduce(::lcm))