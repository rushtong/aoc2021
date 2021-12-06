package day_5

static void main(String[] args) {
    println("Hydrothermal Vents Part 1")
    File file = new File("./input.txt")
    List<String> lines = file.readLines()
    List<XYPairs> pairs = lines.collect { String it -> new XYPairs(it) }
    List<XYPairs> filteredPairs = pairs.findAll { XYPairs it -> it.isHorizontalOrVertical() }

    Integer maxX = filteredPairs.collect {it.maxX() }.max()
    Integer maxY = filteredPairs.collect {it.maxY() }.max()
    int[][] field = new int[maxX][maxY]
    field.each {it.each {it = 0}}


    // Find all XY positions that are occupied by vents
    List<Tuple2<Integer, Integer>> expandedPairs = new ArrayList<Tuple2<Integer, Integer>>()
    filteredPairs.each {expandedPairs.addAll(it.findInclusivePairs()) }

    expandedPairs.each { Tuple2<Integer, Integer> tuple -> {
        int x = tuple.getV1()
        int y = tuple.getV2()
        field[x][y] = field[x][y] + 1
    }}

//    Utils.printField(field)

    def intersections = field.collect {int[] it -> it.findAll {it > 1}}.flatten()
    println(intersections.size())
}

class Utils {

    static void printField(int[][] field) {
        field.each {int[] row ->
            row.each { Integer point -> print( point ?: ".") }
            println("")
        }
    }

}

class XYPairs {
    // Each tuple is an X and a Y pair representing from -> to positions
    Tuple2<Integer, Integer> pair1 // v1 is X, v2 is Y
    Tuple2<Integer, Integer> pair2 // v1 is X, v2 is Y

    XYPairs(String line) {
        List<String> pairStrings = line.split(" -> ")
        List<Integer> pair1Ints = pairStrings[0].split(",").collect {Integer.valueOf(it) }
        List<Integer> pair2Ints = pairStrings[1].split(",").collect {Integer.valueOf(it) }
        pair1 = new Tuple2<>(pair1Ints.get(0), pair1Ints.get(1))
        pair2 = new Tuple2<>(pair2Ints.get(0), pair2Ints.get(1))
    }

    // horizontal and vertical lines: lines where either x1 = x2 or y1 = y2
    boolean isHorizontalOrVertical() {
        (pair1.getV1() == pair2.getV1()) || (pair1.getV2() == pair2.getV2())
    }
    int maxX() {
        [pair1.getV1(), pair2.getV1()].max() + 1
    }
    int maxY() {
        [pair1.getV2(), pair2.getV2()].max() + 1
    }
    List<Tuple2<Integer, Integer>> findInclusivePairs() {
        List<Tuple2<Integer, Integer>> pairList = new ArrayList<>()
        // 1:1 to 1:3 should end up with 1:1, 1:2, 1:3
        if (pair1.getV1() == pair2.getV1()) {
            def range = pair1.getV2()..pair2.getV2()
            range.each {it -> pairList.add(new Tuple2<Integer, Integer>(pair1.getV1(), it))}
        } else if (pair1.getV2() == pair2.getV2()) {
            def range = pair1.getV1()..pair2.getV1()
            range.each {it -> pairList.add(new Tuple2<Integer, Integer>(it, pair1.getV2()))}
        }
        pairList
    }
}