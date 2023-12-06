import java.io.File
import kotlin.math.*

val (timeStr, distanceStr) = File("input.txt").readLines()

val times = timeStr.substringAfter("Time:").trim().split("\\s+".toRegex()).map { it.toInt() }
val distancesToBeat = distanceStr.substringAfter("Distance:").trim().split("\\s+".toRegex()).map { it.toInt() + 1 }

val races = times zip distancesToBeat

// find integer y >= 0, where y = -x^2 + time * x - distance
// a = -1, b = time, c = -distance
val waysToBeat = races.map { (time, distance) ->
    val discriminant = time * time - 4 * (-1) * (-distance)
    val lowerBound = ceil((-time + sqrt(discriminant.toDouble())) / (2 * -1)).toInt()
    val upperBound = floor((-time - sqrt(discriminant.toDouble())) / (2 * -1)).toInt()
    (upperBound - lowerBound + 1).coerceAtLeast(0)
}

println(waysToBeat.reduce(Int::times))