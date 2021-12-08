package day_8

class SegmentSearch {

    static void main(String[] args) {
        boolean train = false
        boolean partOne = true // 519
        println("Seven Segment Search")
        File file = train ? new File("./src/day_8/train.txt") : new File("./src/day_8/input.txt")
        String[] matchingSegments = SegmentSearch.partOne(file);
        println("Final result: " + matchingSegments)
        println("Final count: " + matchingSegments.size())
    }

    // digit segments for 1, 4, 7, and 8 are segment sizes 2, 4, 3, and 7 respectively and uniquely
    // Count those up ...
    static final String[] partOne(File file) {
        file.readLines().collect {line -> {
            String[] halves = line.split("\\|").collect {it.trim()}
            String[] segments = halves.last().split("\\s")
            segments.findAll {[2, 4, 3, 7].contains(it.length())}
        }}.flatten()
    }
}
