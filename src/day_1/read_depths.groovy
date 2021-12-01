package day_1

class readDepths {
    static void main(String[] args) {
        println("Reading Depths")
        File file = new File("./src/day_1/depths.txt")
        List<String> depths = file.readLines()
        List<Integer> depthInts = depths.collect { String it -> Integer.valueOf(it)}
        int increaseCounter = 0
        depths.eachWithIndex { String entry, int i -> 
            if (i == 0) {
                println(entry + " (N/A - no previous measurement)")
            } else {
                if (depthInts[i] > depthInts[i-1]) {
                    println(entry + " (increased)")
                    increaseCounter++
                } else {
                    println(entry + " (decreased)")
                }
            }
        } 
        println("Final number of increases: " + increaseCounter)
    }
}

