import java.io.File
import kotlin.math.abs

val lines: List<String> = File("./small_input").readLines().filter { it.trim() != "" }

data class Line(
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int,
)

data class Point(
    val x: Int,
    val y: Int,
)

fun convertStringToLine(
    str: String
) : Line {
    val (pt1, _, pt2) = str.split(" ")
    val (x1, y1) = pt1.split(",")
    val (x2, y2) = pt2.split(",")

    return Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
}

fun tallyScore(
    board: MutableList<MutableList<Int>>,
    x: Int,
    y: Int
) {
    val hLine = board[y]
    val hCount = hLine[x] + 1
    hLine[x] = hCount

    if (hCount >= 2) overlapPoints.add(Point(x, y))
    board[y] = hLine.toMutableList()
}

fun tallyDiagonalScore(
    line: Line,
    board: MutableList<MutableList<Int>>,
    diff: Int,
    xDecrease: Boolean,
    yDecrease: Boolean
) {
    val (x1, y1, x2, y2) = line
    val xDelta = if (xDecrease) -1 else 1
    val yDelta = if (yDecrease) -1 else 1

    for (i in 0..abs(diff)) {
        val xDir = x1 + (i * xDelta)
        val yDir = y1 + (i * yDelta)
        tallyScore(board, xDir, yDir)
    }
}

fun printBoard(
    board: MutableList<MutableList<Int>>
) {
    board.forEach {
        var str = it.joinToString("")
        println(str.replace("0", "."))
    }
}

var maxX = 0
var maxY = 0

val convertedLines = lines.map { line ->
    val newLine = convertStringToLine(line)
    val (x1, y1, x2, y2) = newLine

    maxX = maxOf(maxX, x1, x2)
    maxY = maxOf(maxY, y1, y2)
    newLine
}

var pointsBoard = MutableList(maxY + 1) { MutableList(maxX + 1) { 0 } }
var overlapPoints = mutableSetOf<Point>()

convertedLines.forEach {
    val (x1, y1, x2, y2) = it
    if (y1 == y2) {
        for (i in minOf(x1, x2)..maxOf(x1, x2)) {
            tallyScore(pointsBoard, i, y1)
        }
    } else if (x1 == x2) {
        for (i in minOf(y1, y2)..maxOf(y1, y2)) {
            tallyScore(pointsBoard, x1, i)
        }
    }
}

printBoard(pointsBoard)
println("Part1: ${overlapPoints.size}")
println("-----------------")

pointsBoard = MutableList(maxY + 1) { MutableList(maxX + 1) { 0 } }

convertedLines.forEach {
    val (x1, y1, x2, y2) = it
    if (y1 == y2) {
        for (i in minOf(x1, x2)..maxOf(x1, x2)) {
            tallyScore(pointsBoard, i, y1)
        }
    } else if (x1 == x2) {
        for (i in minOf(y1, y2)..maxOf(y1, y2)) {
            tallyScore(pointsBoard, x1, i)
        }
    } else if (x1 == y1 && x2 == y2) {
        for (i in minOf(x1, x2)..maxOf(y1, y2)) {
            tallyScore(pointsBoard, i, i)
         }
    } else {
        val diff = x2 - x1
        if (x2 - x1 < 0 && y2 - y1 < 0) {
            tallyDiagonalScore(it, pointsBoard, diff, true, true)
        } else if (x2 - x1 < 0 && y2 - y1 > 0) {
            tallyDiagonalScore(it, pointsBoard, diff, true, false)
        } else if (x2 - x1 > 0 && y2 - y1 > 0) {
            tallyDiagonalScore(it, pointsBoard, diff, false, false)
        } else {
            tallyDiagonalScore(it, pointsBoard, diff, false, true)
        }
    }
}

printBoard(pointsBoard)
println("Part2: ${overlapPoints.size}")