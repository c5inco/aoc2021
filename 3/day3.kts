import java.io.File
import java.util.Collections

val lines: List<String> = File("./input").readLines()
val smallLines: List<String> = File("./small_input").readLines()

fun createBitsMatrix(bitsList: List<String>) : List<List<Int>> {
    val matrix = mutableListOf<MutableList<Int>>()
    bitsList.forEach { line ->
        line.forEachIndexed { idx, char ->
            val bit = Character.getNumericValue(char)
            val bits = matrix.getOrNull(idx)
            if (bits == null) {
                matrix.add(mutableListOf(bit))
            } else {
                bits.add(bit)
                matrix[idx] = bits.toMutableList()
            }
        }
    }
    return matrix.toList()
}

var bitsMatrix = createBitsMatrix(lines)
var gamma = List(bitsMatrix.size) { idx ->
    if (Collections.frequency(bitsMatrix[idx], 1) > Collections.frequency(bitsMatrix[idx], 0)) {
        1
    } else {
        0
    }
}


var epsilon = List(bitsMatrix.size) { idx ->
    if (Collections.frequency(bitsMatrix[idx], 1) < Collections.frequency(bitsMatrix[idx], 0)) {
        1
    } else {
        0
    }
}

println("Part1: ${gamma.joinToString(separator = "").toInt(2) * epsilon.joinToString(separator = "").toInt(2)}")

fun reduceNumbers(
        idx: Int,
        numbers: List<String>,
        criteria: (matrix: List<List<Int>>, index: Int) -> Int
) : List<String> {
    if (numbers.size == 1) {
        return numbers
    } else {
        val matrix = createBitsMatrix(numbers)
        val nextBit = criteria(matrix, idx)

        return numbers.filter {
            Character.getNumericValue(it[idx]) == nextBit
        }
    }
}

val o2Pass = lines.foldIndexed(lines) { index: Int, numbers: List<String>, _ ->
    reduceNumbers(index, numbers) { matrix, idx ->
        if (Collections.frequency(matrix[idx], 1) >= Collections.frequency(matrix[idx], 0)) 1 else 0
    }
}

val co2Pass = lines.foldIndexed(lines) { index: Int, numbers: List<String>, element: String ->
    reduceNumbers(index, numbers) { matrix, idx ->
        if (Collections.frequency(matrix[idx], 0) <= Collections.frequency(matrix[idx], 1)) 0 else 1
    }
}

println("Part2: ${o2Pass[0].toInt(2) * co2Pass[0].toInt(2)}")