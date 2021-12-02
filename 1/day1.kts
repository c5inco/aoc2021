import java.io.File

val lines: List<String> = File("./input").readLines()
var larger = 0
var prev = 0

lines.forEachIndexed { idx, line ->
    val curr = line.toInt()
    if (idx != 0 && curr > prev) larger++
    prev = curr
}

println("Part1: $larger")

larger = 0
prev = 0

lines.windowed(size = 3).mapIndexed { idx, window ->
    val curr = window.map { it.toInt() }.toList().sum()
    if (idx != 0 && curr > prev) larger++
    prev = curr
}

println("Part2: $larger")