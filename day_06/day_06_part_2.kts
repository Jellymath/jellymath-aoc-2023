import java.io.File
import kotlin.math.*

val (timeStr, distanceStr) = File("input.txt").readLines()

val time = timeStr.substringAfter("Time:").filter { it.isDigit() }.toLong()
val minWinDistance = distanceStr.substringAfter("Distance:").filter { it.isDigit() }.toLong() + 1

// find integer y >= 0, where y = -x^2 + time * x - distance
// a = -1, b = time, c = -distance
val discriminant = time * time - 4 * (-1) * (-minWinDistance)
val lowerBound = ceil((-time + sqrt(discriminant.toDouble())) / (2 * -1)).toLong()
val upperBound = floor((-time - sqrt(discriminant.toDouble())) / (2 * -1)).toLong()
val waysToBeat = (upperBound - lowerBound + 1).coerceAtLeast(0)
println(waysToBeat)