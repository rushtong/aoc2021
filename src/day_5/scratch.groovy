package day_5

static void main(String[] args) {
    println("Grid Test")

//    Diagonal down, left -> right
//    Tuple2<Integer, Integer> from = new Tuple2<>(0,0)
//    Tuple2<Integer, Integer> to = new Tuple2<>(4,4)

//    Diagonal up, right -> left
//    Tuple2<Integer, Integer> from = new Tuple2<>(4,4)
//    Tuple2<Integer, Integer> to = new Tuple2<>(0,0)

//    Diagonal up, left -> right
//    Tuple2<Integer, Integer> from = new Tuple2<>(0,4)
//    Tuple2<Integer, Integer> to = new Tuple2<>(4,0)

//    Horizontal line, left -> right
    Tuple2<Integer, Integer> from = new Tuple2<>(0,0)
    Tuple2<Integer, Integer> to = new Tuple2<>(0,4)

//    Horizontal line, right -> left
//    Tuple2<Integer, Integer> from = new Tuple2<>(4,0)
//    Tuple2<Integer, Integer> to = new Tuple2<>(4,4)

//    Tuple2<Integer, Integer> from = new Tuple2<>(2,0)
//    Tuple2<Integer, Integer> to = new Tuple2<>(2,4)

    // If from x < to x, we're moving forward.
    //      Increment x position (decrement when going backwards) if there is a difference between the two
    // If from y < to y, we're moving down.
    //      Increment y position (decrement when going up) if there is a difference between the two

    // This doesn't seem to work for vertical or horizontal lines yet

    boolean forward = from.getV1() < to.getV1()
    boolean incrementXPosition = from.getV1() != to.getV1()
    boolean down = from.getV2() < to.getV2()
    boolean incrementYPosition = from.getV2() != to.getV2()

    int maxX = 10
    int maxY = 5
    int[][] field = new int[maxY][maxX]
    if (forward) {
        if (down) {
            def xPos = from.getV1()
            def yPos = from.getV2()
            while(xPos <= to.getV1() && xPos < maxX && yPos <= to.getV2() && yPos < maxY) {
                field[yPos][xPos] = field[yPos][xPos] + 1
                if (incrementXPosition) xPos++
                if (incrementYPosition) yPos++
            }
        } else {
            def xPos = from.getV1()
            def yPos = from.getV2()
            while((xPos <= to.getV1() && xPos < maxX) && (yPos <= to.getV2() && yPos < maxY)) {
                field[yPos][xPos] = field[yPos][xPos] + 1
                if (incrementXPosition) xPos++ // Going forward
                if (incrementYPosition) yPos-- // Going up
            }
        }
    } else { // Backward
        if (down) {
            def xPos = from.getV1()
            def yPos = from.getV2()
            while(xPos >= to.getV1() && xPos < maxX && yPos >= to.getV2() && yPos < maxY) {
                field[yPos][xPos] = field[yPos][xPos] + 1
                if (incrementXPosition) xPos-- // Going backward
                if (incrementYPosition) yPos++ // Going down
            }
        } else {
            def xPos = from.getV1()
            def yPos = from.getV2()
            while(xPos >= to.getV1() && xPos < maxX && yPos >= to.getV2() && yPos < maxY) {
                field[yPos][xPos] = field[yPos][xPos] + 1
                if (incrementXPosition) xPos-- // Going backward
                if (incrementYPosition) yPos-- // Going up
            }
        }
    }

    field.each {println(it)}
}

