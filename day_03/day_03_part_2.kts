import java.io.File

val input = File("input.txt").readLines()

val numberRegex = Regex("\\d+")
val numberRanges = input.flatMapIndexed { i, line ->
    numberRegex.findAll(line).map { Triple(it.value.toInt(), i, it.range) }.toList()
}.filterNot { (_, _, ranges) -> ranges.isEmpty() }

val searchBoxes = numberRanges.map { numWithPosition ->
    val (_, i, range) = numWithPosition
    numWithPosition to (i - 1..i + 1).flatMap { iNew -> (range.first - 1..range.last + 1).map { jNew -> iNew to jNew } }
}

val numToGears = searchBoxes.map { (numWithPosition, searches) ->
    numWithPosition to searches.filter { (i, j) -> input.getOrNull(i)?.getOrNull(j) == '*' }
}.filterNot { (_, searches) -> searches.isEmpty() }

val gearNumbers = numToGears.flatMap { (numWithPosition, searches) -> searches.map { numWithPosition to it } }
    .groupBy({ (_, possibleGear) -> possibleGear }, { (numWithPosition, _) -> numWithPosition })
    .values
    .filter { it.size == 2 }

println(gearNumbers.sumOf { (first, second) ->
    val (firstNum, _, _) = first
    val (secondNum, _, _) = second
    firstNum * secondNum
})

