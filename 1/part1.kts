import java.io.File

val lines: List<String> = File("./input").readLines()
var larger = 0
var prev = 0

lines.forEachIndexed { idx, line ->
    val curr = line.toInt()
    if (idx != 0 && curr > prev) larger++
    prev = curr
}

println(larger)