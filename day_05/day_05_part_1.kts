import java.io.File

val input = File("input.txt").readText()

data class Range(val fromStart: Long, val toStart: Long, val length: Long)
class RangeMap(private val ranges: List<Range>): (Long) -> Long {
    override operator fun invoke(key: Long): Long {
        val range = ranges.firstOrNull { key in it.fromStart..<(it.fromStart + it.length) } ?: return key
        return key + range.toStart - range.fromStart
    }
}
val nl = "\r\n"
fun parseRanges(from: String) = RangeMap(input.substringAfter(from).substringBefore("$nl$nl").lines().map {
    val (destinationFrom, originFrom, rangeLength) = it.split(" ")
    Range(originFrom.toLong(), destinationFrom.toLong(), rangeLength.toLong())
})
val seeds = input.substringAfter("seeds: ").substringBefore(nl).split(" ").map { it.toLong() }
val seedToSoil = parseRanges("seed-to-soil map:$nl")
val soilToFertilizer = parseRanges("soil-to-fertilizer map:$nl")
val fertilizerToWater = parseRanges("fertilizer-to-water map:$nl")
val waterToLight = parseRanges("water-to-light map:$nl")
val lightToTemperature = parseRanges("light-to-temperature map:$nl")
val temperatureToHumidity = parseRanges("temperature-to-humidity map:$nl")
val humidityToLocation = parseRanges("humidity-to-location map:$nl")

fun <T> ((T) -> T).andThen(f: (T) -> T): (T) -> T = {  f(this(it)) }
val seedToLocation = seedToSoil
    .andThen(soilToFertilizer)
    .andThen(fertilizerToWater)
    .andThen(waterToLight)
    .andThen(lightToTemperature)
    .andThen(temperatureToHumidity)
    .andThen(humidityToLocation)

val locations = seeds.map(seedToLocation)
println(locations.min())