package day_8

class SegmentSearch {

    static void main(String[] args) {
        boolean train = true
        boolean partOne = true
        println("Seven Segment Search")
        File file = train ? new File("./src/day_8/train.txt") : new File("./src/day_8/input.txt")
        file.readLines().each {line -> {
            String[] halves = line.split("\\|").collect {it.trim()}
            println(halves)
        }}
    }

}
