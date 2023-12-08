import java.io.File

val input = """
    LR

    11A = (11B, XXX)
    11B = (XXX, 11Z)
    11Z = (11B, XXX)
    22A = (22B, XXX)
    22B = (22C, 22C)
    22C = (22Z, 22Z)
    22Z = (22B, 22B)
    XXX = (XXX, XXX)
""".trimIndent().lines()

val instructions = input.first()
val regex = """([A-Z0-9]{3}) = \(([A-Z0-9]{3})\, ([A-Z0-9]{3})\)""".toRegex()
val routes = input.drop(2).associate {
    val (_, from, left, right) = regex.matchEntire(it)!!.groupValues
    from to (left to right)
}

fun move(from: String, direction: Char) = routes.getValue(from)
        .let { (l, r) -> if (direction == 'L') l else r }

val starts = routes.keys.filter { it.endsWith("A") }
println(starts.size)

val cycles = generateSequence(starts) {
    if (it.all { it.endsWith("Z") }) null else it.map { instructions.fold(it, ::move) }
}

println((cycles.count() - 1) * instructions.length)