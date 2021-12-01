package day_1

class readDepthWindows {
    static void main(String[] args) {
        println("Reading Depth Windows")
        File file = new File("./src/day_1/depths.txt")
        List<String> depths = file.readLines()
        List<Integer> depthInts = depths.collect { String it -> Integer.valueOf(it)}
        List<Tuple3<Integer, Integer, Integer>> tuples = new ArrayList<>() 
        depthInts.eachWithIndex { int entry, int i -> {
                if (i < 2) {
                    // do nothing with rows 0 and 1.
                } else {
                    // Construct a new triple from the previous 3 elements
                    Tuple3<Integer, Integer, Integer> tuple = new Tuple3(depthInts[i-2], depthInts[i-1], entry)
                    tuples.add(tuple)
                }
            }
        }
        int increaseCounter = 0
        tuples.eachWithIndex { Tuple3 tuple, int i ->
            int sum = tuple.getV1() + tuple.getV2() + tuple.getV3()
            if (i == 0) {
                println(sum + " (N/A - no previous sum)")
            } else {
                int previousSum = tuples[i-1].getV1() + tuples[i-1].getV2() + tuples[i-1].getV3()
                if (sum > previousSum) {
                    println(sum + " (increased)")
                    increaseCounter++
                } else {
                    println(sum + " (decreased)")
                }
            }
        } 
        println("Final number of increases: " + increaseCounter)
    }
}
