package day_13

import com.sun.tools.javac.util.Pair

class TransparentOrigami {

    static void main(String[] args) {
        println("Transparent Origami")
        boolean train = false
        File file = train ? new File("./src/day_13/train.txt") : new File("./src/day_13/input.txt")
        List<String> lines = file.readLines()

        // A Pair is a [column][row] combination
        List<Pair<Integer, Integer>> pairs = makePairs(lines)
        List<String> instructions = lines.findAll {it.startsWith("fold along ")}
        foldPairsWithInstructions(pairs, instructions)
    }

    static void foldPairsWithInstructions(List<Pair<Integer, Integer>> pairs, List<String> instructions) {
        // Starting min column and row sizes
        int minColSize = pairs.collect {it.fst}.max()
        int minRowSize = pairs.collect { it.snd}.max()
        List<Pair<Integer, Integer>> foldedPairs = pairs
        instructions.each { line ->
            int foldAxis = Integer.valueOf(line.split("=")[1])
            if (line.startsWith("fold along y=")) {
                foldedPairs = foldUpOnRow(foldedPairs, foldAxis)
                minRowSize = foldAxis - 1
            }
            else if (line.startsWith("fold along x=")) {
                foldedPairs = foldLeftOnColumn(foldedPairs, foldAxis)
                minColSize = foldAxis - 1
            }
            println()
            println(line)
            int counts = graphPairs(foldedPairs, minColSize, minRowSize)
            println("Hashes: " + counts)
        }
    }

    static List<Pair<Integer, Integer>> foldUpOnRow(List<Pair<Integer, Integer>> pairs, int foldRow) {
        // A Pair is a [column][row] combination
        pairs.collect { pair ->
            // keep everything above the fold in place
            if (pair.snd < foldRow) {
                pair
            }
            // merge rows below the fold up by offset
            else {
                int offset = pair.snd - foldRow
                int newRow = foldRow - offset
                new Pair<Integer, Integer>(pair.fst, newRow)
            }
        }
    }

    static List<Pair<Integer, Integer>> foldLeftOnColumn(List<Pair<Integer, Integer>> pairs, int foldColumn) {
        // A Pair is a [column][row] combination
        pairs.collect {pair ->
            // keep the left hand columns in place
            if (pair.fst < foldColumn) {
                pair
            }
            // merge rows right of the fold to the left by offset
            else {
                int offset = pair.fst - foldColumn
                int newCol = foldColumn - offset
                new Pair<Integer, Integer>(newCol, pair.snd)
            }
        }
    }

    static List<Pair<Integer, Integer>> makePairs(List<String> lines) {
        lines.findResults {line ->
            if (line.contains(",")) {
                String[] coordinate = line.split(",")
                new Pair<Integer, Integer>(Integer.valueOf(coordinate[0]), Integer.valueOf(coordinate[1]))
            } else {
                null
            }
        } as List<Pair>
    }

    static int graphPairs(List<Pair<Integer, Integer>> pairs, int minColumnSize, int minRowSize) {
        // A Pair is a [column][row] combination
        int maxColSize = [minColumnSize, pairs.collect {it.fst}].flatten().max() as int
        int maxRowSize = [minRowSize, pairs.collect {it.snd}].flatten().max() as int
        int hashes = 0
        (0..maxRowSize).each { row ->
            (0..maxColSize).each {column ->
                Pair<Integer, Integer> thisCell = new Pair<>(column, row)
                if (pairs.contains(thisCell)) {
                    hashes++
                    print("#")
                }
                else {
                    print(".")
                }
            }
            println()
        }
        hashes
    }
}