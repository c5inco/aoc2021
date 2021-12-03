import java.io.File

val lines: List<String> = File("./input").readLines()

var horizontalPos = 0
var depth = 0

lines.forEach { line ->
    val (cmd, value) = line.split(" ")
    val num = value.toInt()

    when (cmd) {
        "up" -> depth -= num
        "down" -> depth += num
        else -> horizontalPos += num
    }
}

println("Part1: ${horizontalPos * depth}")

horizontalPos = 0
depth = 0
var aim = 0

lines.forEach { line ->
    val (cmd, value) = line.split(" ")
    val num = value.toInt()

    when (cmd) {
        "up" -> aim -= num
        "down" -> aim += num
        else -> {
            horizontalPos += num
            depth += aim * num
        }
    }
}

println("Part2: ${horizontalPos * depth}")