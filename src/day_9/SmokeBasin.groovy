package day_9

class SmokeBasin {
    static void main(String[] args) {
        boolean train = false
        boolean partOne = true
        println("Smoke Basin")
        File file = train ? new File("./src/day_9/train.txt") : new File("./src/day_9/input.txt")
        Integer[][] depths = file.readLines().collect { line ->
            line.split("").collect{Integer.valueOf(it)}
        }
        if (partOne) {
            List<Tuple2<Integer, Integer>> lowPoints = findLowPoints(depths)
            List<Integer> lowPointDepths = lowPoints.collect{tuple ->
                getCell(depths, tuple.getV1(), tuple.getV2())
            }
            printDepths(depths, lowPoints)
            println(lowPoints)
            println(lowPointDepths)
            println(lowPointDepths.sum())
            println(lowPointDepths.size())
            Integer riskScore = lowPointDepths.collect{Integer d -> d + 1}.sum() as Integer
            println("Risk Level: " + riskScore)
        }
    }

    static void printDepths(Integer[][] depths, List<Tuple2<Integer, Integer>> lowPoints) {
        String[][] printable = depths.collect {rows -> rows.collect {cell -> " " + String.valueOf(cell) + " "}}
        lowPoints.each {point -> printable[point.getV1()][point.getV2()] = "[" + printable[point.getV1()][point.getV2()].trim() + "]" }
        println("---" * printable[0].size())
        printable.each {println(it.join(""))}
        println("---" * printable[0].size())
    }

    static List<Tuple2<Integer, Integer>> findLowPoints(Integer[][] depths) {
        List<Tuple2<Integer, Integer>> lowPoints = new ArrayList<>()
        depths.eachWithIndex { Integer[] row, int rowIndex ->
            row.eachWithIndex{ int depth, int colIndex ->
                List<Integer> neighbors = findNeighbors(depths, rowIndex, colIndex)
                if (neighbors.every {Integer neighbor -> neighbor > depth}) {
                    lowPoints.add(new Tuple2<>(rowIndex, colIndex))
                }
            }
        }
        lowPoints
    }

    static List<Integer> findNeighbors(Integer[][] depths, int row, int col) {
        [getCell(depths, row - 1, col),
         getCell(depths, row + 1, col),
         getCell(depths, row, col - 1),
         getCell(depths, row, col + 1)].findAll { Objects.nonNull(it) }
    }

    static Integer getCell(Integer[][] depths, int row, int col) {
        if (row < 0 || col < 0 || row > depths.length - 1 || col > depths[0].length - 1) null
        else {
            try {
                depths[row][col]
            } catch (ArrayIndexOutOfBoundsException ignored) {
                null
            }
        }
    }
}