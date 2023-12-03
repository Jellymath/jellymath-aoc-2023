import java.io.File

val input = File("input.txt").readLines()

val numberRegex = Regex("\\d+")
val numberRanges = input.flatMapIndexed { i, line ->
    numberRegex.findAll(line).map { Triple(it.value.toInt(), i, it.range) }.toList()
}.filterNot { (_, _, ranges) -> ranges.isEmpty() }

val searchBoxes = numberRanges.map { (num, i, range) ->
    num to (i - 1..i + 1).flatMap { iNew -> (range.first - 1..range.last + 1).map { jNew -> iNew to jNew } }
}

val partNumbers = searchBoxes.filter { (_, searches) ->
    searches.any { (i, j) ->
        val char = input.getOrNull(i)?.getOrNull(j)
        char != null && char != '.' && !char.isDigit()
    }
}.map { (num, _) -> num }

println(partNumbers.sum())