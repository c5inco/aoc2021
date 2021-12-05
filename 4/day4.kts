import java.io.File

val lines: List<String> = File("./input").readLines().filter { it.trim() != "" }

val numbersToDraw = lines
    .subList(0, 1)
    .first()
    .split(",")
    .map { it.toInt() }
val boardsData = lines
    .subList(1, lines.size)
    .windowed(5, 5)
    .map { board ->
        val rows = board
            .map { row: String ->
                row
                    .trim()
                    .split("\\s+".toRegex())
                    .map { it.toInt() }
            }
            .toMutableList()

        val cols = rows.mapIndexed { idx, _ ->
            rows.map { it[idx] }
        }

        rows.addAll(cols)
        rows
    }

fun scoreBoard(
        board: List<List<Int>>,
        winningNumber: Int
) : Int {
    return board
            .flatten()
            .distinct()
            .filter { it != winningNumber }
            .sum() * winningNumber
}

val boardsScore = boardsData.toMutableList()
var stopDrawing = false
var winningBoard = -1
var winningNumber = -1

for(drawnNumber in numbersToDraw) {
    for((bdIdx, bd) in boardsData.withIndex()) {
        for((ridx, numbers) in bd.withIndex()) {
            val remainingNumbers = boardsScore[bdIdx][ridx].toMutableList()
            remainingNumbers.remove(drawnNumber)
            boardsScore[bdIdx][ridx] = remainingNumbers.toList()
            stopDrawing = remainingNumbers.isEmpty()
            if (stopDrawing) break
        }
        if (stopDrawing) {
            winningBoard = bdIdx
            break
        }
    }
    if (stopDrawing) {
        winningNumber = drawnNumber
        break
    }
}

println("Part1: ${scoreBoard(boardsScore[winningBoard], winningNumber)}")

val lastBoardsScore = boardsData.toMutableList()
var boardsWon = mutableSetOf<Int>()
var lastBoardWon = -1
var lastWinningNumber = -1

for(drawnNumber in numbersToDraw) {
    for((bdIdx, bd) in boardsData.withIndex()) {
        if (!boardsWon.contains(bdIdx)) {
            var boardWon = false
            for((ridx, numbers) in bd.withIndex()) {
                val remainingNumbers = lastBoardsScore[bdIdx][ridx].toMutableList()
                remainingNumbers.remove(drawnNumber)
                lastBoardsScore[bdIdx][ridx] = remainingNumbers.toList()

                if (remainingNumbers.isEmpty()) {
                    boardWon = true
                    boardsWon.add(bdIdx)
                    break
                }
            }
            if (boardsWon.size == boardsData.size) {
                lastBoardWon = bdIdx
                lastWinningNumber = drawnNumber
                break
            }
        }
    }
}

println("Part2: ${scoreBoard(lastBoardsScore[lastBoardWon], lastWinningNumber)}")