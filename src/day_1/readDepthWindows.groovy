package day_1

class readDepthWindows {
    static void main(String[] args) {
        println("Reading Depth Windows")
        File file = new File("./src/day_1/depths.txt")
        List<String> depths = file.readLines()
        List<Integer> depthInts = depths.collect { String it -> Integer.valueOf(it)}
        List<Triple> triples = new ArrayList<>() 
        depthInts.eachWithIndex { int entry, int i -> {
                if (i < 2) {
                    // do nothing with rows 0 and 1.
                } else {
                    // Construct a new triple from the previous 3 elements
                    Triple triple = new Triple()
                    triple.setIndex(i)
                    triple.setA(depthInts[i-2])
                    triple.setB(depthInts[i-1])
                    triple.setC(entry)
                    triples.add(triple)
                }
            }
        } 
        int increaseCounter = 0
        triples.eachWithIndex { Triple triple, int i ->
            if (i == 0) {
                println(triple.sum() + " (N/A - no previous sum)")
            } else {
                if (triples[i].sum() > triples[i-1].sum()) {
                    println(triple.sum() + " (increased)")
                    increaseCounter++
                } else {
                    println(triple.sum() + " (decreased)")
                }
            }
        } 
        println("Final number of increases: " + increaseCounter)
    }
}

class Triple {
    int index
    int A
    int B
    int C
    int sum() { A + B + C }
    String toString() {  "index: " + index + " A: " + A + " B: " + B + " C: " + C + " sum: " + sum() }
}