package day_5

static void main(String[] args) {
    // 6658 too low, 15191 is also incorrect. Looks like my x and ys are off in the grid ...
    println("Hydrothermal Vents Part 2")
    File file = new File("./input.txt")
    List<String> lines = file.readLines()
    List<XYPairs> allPairs = lines.collect { String it -> new XYPairs(it) }
    List<XYPairs> horizontalOrVerticalPairs = allPairs.findAll { XYPairs it -> it.isHorizontalOrVertical() }
    List<XYPairs> diagonalPairs = allPairs.findAll { XYPairs it -> !it.isHorizontalOrVertical() }

    Integer maxX = allPairs.collect {it.maxX() }.max()
    Integer maxY = allPairs.collect {it.maxY() }.max()
    int[][] field = new int[maxX][maxY]
    field.each {it.each {it = 0}}

    // Plot diagonals first
    int[][] plottedField = Plot.plotPairs(diagonalPairs, field)

    // For horizontal and vertical lines, plot those directly
    List<Tuple2<Integer, Integer>> expandedPairs = new ArrayList<Tuple2<Integer, Integer>>()
    horizontalOrVerticalPairs.each {expandedPairs.addAll(it.findInclusivePairs()) }
    expandedPairs.each { Tuple2<Integer, Integer> tuple -> {
        int x = tuple.getV1()
        int y = tuple.getV2()
        plottedField[x][y] = plottedField[x][y] + 1
    }}


    plottedField.each {println(it)}

    def intersections = plottedField.collect {int[] it -> it.findAll {it > 1}}.flatten()
    println(intersections.size())
}

class Plot {
    static int[][] plotPairs(List<XYPairs> pairs, int[][] field) {
        int[][] newField = field.clone()
        pairs.each { XYPairs xyPair -> {
            // If from x < to x, we're moving forward. Increment x position (decrement when going backwards)
            // If from y < to y, we're moving down. Increment y position (decrement when going up)
            boolean forward = xyPair.pair1.getV1() < xyPair.pair2.getV1()
            boolean down = xyPair.pair1.getV2() < xyPair.pair2.getV2()

            if (forward) {
                if (down) {
                    def xPos = xyPair.pair1.getV1()
                    def yPos = xyPair.pair1.getV2()
                    while(xPos <= xyPair.pair2.getV1() && yPos <= xyPair.pair2.getV1()) {
                        newField[xPos][yPos] = newField[xPos][yPos] + 1
                        xPos++ // Going forward
                        yPos++ // Going down
                    }
                } else {
                    def xPos = xyPair.pair1.getV1()
                    def yPos = xyPair.pair1.getV2()
                    while(xPos <= xyPair.pair2.getV1() && yPos <= xyPair.pair2.getV1()) {
                        newField[xPos][yPos] = newField[xPos][yPos] + 1
                        xPos++ // Going forward
                        yPos-- // Going up
                    }
                }
            } else { // Backward
                if (down) {
                    def xPos = xyPair.pair1.getV1()
                    def yPos = xyPair.pair1.getV2()
                    while(xPos >= xyPair.pair2.getV1() && yPos >= xyPair.pair2.getV1()) {
                        newField[xPos][yPos] = newField[xPos][yPos] + 1
                        xPos-- // Going backward
                        yPos++ // Going down
                    }
                } else {
                    def xPos = xyPair.pair1.getV1()
                    def yPos = xyPair.pair1.getV2()
                    while(xPos >= xyPair.pair2.getV1() && yPos >= xyPair.pair2.getV1()) {
                        newField[xPos][yPos] = newField[xPos][yPos] + 1
                        xPos-- // Going backward
                        yPos-- // Going up
                    }
                }
            }
        }}
        newField
    }
}


