import java.io.File

val input = File("input.txt").readLines()

val instructions = input.first()
val regex = """([A-Z]{3}) = \(([A-Z]{3})\, ([A-Z]{3})\)""".toRegex()
val routes = input.drop(2).associate {
    val (_, from, left, right) = regex.matchEntire(it)!!.groupValues
    from to (left to right)
}

fun move(from: String, direction: Char) = routes.getValue(from)
        .let { (l, r) -> if (direction == 'L') l else r }

val cycles = generateSequence("AAA") {
    if (it == "ZZZ") null else instructions.fold(it, ::move)
}

println((cycles.count() - 1) * instructions.length)