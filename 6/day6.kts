import java.io.File

val lines: List<String> = File("./small_input").readLines().filter { it.trim() != "" }
val lanternFish = lines[0]
        .split(",")
        .map { it.toInt() }
        .toMutableList()

println("Initial state: $lanternFish")

val part1Days = 80

for (i in 1..part1Days) {
    var newFish = 0
    lanternFish.forEachIndexed { idx, fish ->
        if (fish == 0) {
            lanternFish[idx] = 6
            newFish++
        } else {
            lanternFish[idx] = fish - 1
        }
    }
    for (j in 0 until newFish) { lanternFish.add(8) }

    //println("After $i ${if (i > 1) "days" else "day"}: $lanternFish")
}

println("Part1: After $part1Days days - ${lanternFish.size}")

val part2Days = 256
val days = MutableList<Long>(9) { 0 }
val fish = lines[0]
        .split(",")
        .map { it.toInt() }

fish.map {
    days[it] = (days[it] + 1).toLong()
}

for (i in 0 until part2Days) {
    var today = i % days.size

    days[(today + 7) % days.size] += days[today]
}

println("Part2: After $part2Days days - ${days.sum()}")

