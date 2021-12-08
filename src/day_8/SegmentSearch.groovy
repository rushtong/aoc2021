package day_8

class SegmentSearch {

    static void main(String[] args) {
        boolean train = false
        boolean partOne = false // Part 1 solution: 519, part 2: 1027483
        println("Seven Segment Search")
        File file = train ? new File("./src/day_8/train.txt") : new File("./src/day_8/input.txt")
        if (partOne) {
            String[] matchingSegments = SegmentSearch.partOne(file)
            println("Final result: " + matchingSegments)
            println("Final count: " + matchingSegments.size())
        } else {
            println("Signal Map")
            List<String> lines = file.readLines()
            List<String[]> linePairs = lines.collect {it.split("\\|") }
            def values = linePairs.collect {pair ->
                String[] sigPatternVals = pair.first().trim().split("\\s")
                Map<String, Integer> patternMap = signalPatternMap(sigPatternVals)
                println(patternMap)
                String[] values = pair.last().trim().split("\\s").collect {it.split("").sort().join()}
                println(values)
                List<Integer> mappedValues = values.collect {val -> patternMap.get(val)}
                println(mappedValues.join())
                Integer.valueOf(mappedValues.join())
            }
            println("SUM: " + values.sum())
        }
    }

    static Map<String, Integer> signalPatternMap(String[] segments) {
        Map<String, Integer> segmentMap = new TreeMap<>()
        segments.each {
            String sortedSegment = it.split("").sort().join("")
            if (it.size() == 2) {
                segmentMap.put(sortedSegment, 1)
            }
            if (it.size() == 4) {
                segmentMap.put(sortedSegment, 4)
            }
            if (it.size() == 3) {
                segmentMap.put(sortedSegment, 7)
            }
            if (it.size() == 7) {
                segmentMap.put(sortedSegment, 8)
            }
        }
        segments.each {String segment ->
            // 0, 6, and 9 all have count 6
            if (segment.size() == 6) {
                String sortedSegment = segment.split("").sort().join("")
                // 0 and 9 contain all elements of 1, 6 has only a single element of 1
                List<String> seg1Vals = segmentMap.find {it.value == 1}.key.split("")
                List<String> segmentVals = sortedSegment.split("")
                List<String> intersection = seg1Vals.intersect(segmentVals)
                if (intersection.size() == 1) {
                    segmentMap.put(sortedSegment, 6)
                } else {
                    // 9 contains all elements of 4, but 0 only contains 3 elements of 4
                    List<String> seg4Vals = segmentMap.find {it.value == 4}.key.split("")
                    List<String> intersection2 = seg4Vals.intersect(segmentVals)
                    if (intersection2.size() == 4) {
                        segmentMap.put(sortedSegment, 9)
                    } else if (intersection2.size() == 3) {
                        segmentMap.put(sortedSegment, 0)
                    }
                }
            }
        }
        segments.each{String segment ->
            String sortedSegment = segment.split("").sort().join("")
            // 2, 3, and 5 all have count 5
            if (segment.size() == 5) {
                // 3 contains all of the elements of 1; 2 and 5 only contain a single element of 1
                List<String> seg1Vals = segmentMap.find {it.value == 1}.key.split("")
                List<String> segmentVals = segment.split("")
                List<String> intersection = seg1Vals.intersect(segmentVals)
                if (intersection.size() == 2) {
                    segmentMap.put(sortedSegment, 3)
                } else {
                    // 6 contains all the elements of 5 but only 3 elements of 2
                    List<String> seg6Vals = segmentMap.find {it.value == 6}.key.split("")
                    List<String> intersection2 = seg6Vals.intersect(segmentVals)
                    if (intersection2.size() == segmentVals.size()) {
                        segmentMap.put(sortedSegment, 5)
                    } else {
                        segmentMap.put(sortedSegment, 2)
                    }
                }
            }
        }
        segmentMap.sort {it.value}
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
