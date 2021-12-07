package day_6

// Part 1 runs out of heap space around 129
// Day 80 is (366057) for this dataset

static void main(String[] args) {
    println("Lantern Fish Part 2")
    File file = new File("./input.txt")
    Collection<Integer> fish = file.readLines().get(0).split(',').collect {Integer.valueOf(it) }
    println("Initial State (" + fish.size() + "): " + fish)

// List based approach does slightly better ... OOM around 137-ish
//    def range = 1..256
//    range.each {day ->
//        List<Integer> newFish = FishUtil.incrementFishIntegers(fish)
//        println("After  day " + day + ": (" + newFish.size() + ")")
//        fish = newFish
//    }

    // Split the long list into a list of lists so we can iterate
    // over one fish for its lifetime and then tally them up.
    // Looks like this version is topping out around 158-ish
//    List<List<Integer>> fishMap = fish.collect {[it] }
//    def range = 1..256
//    range.each { day ->
//        fishMap.eachWithIndex {fishSubList, index ->
//            List<Integer> newFish = FishUtil.incrementFishIntegers(fishSubList)
//            fishMap[index] = newFish
//        }
//        def daySum = fishMap.collect {it.size() }.sum()
//        println("After  day " + day + ": (" + daySum + ")")
//    }

    // Iterate over each fish in the original map for all days, then sum them up
    // Still too slow.
    List<List<Integer>> fishMap = fish.collect {[it] }
    Integer interimCounter = 0
    def range = 1..175
    fishMap.eachWithIndex {fishSubList, index ->
        def fishSubListCopy = fishSubList
        range.each { day ->
            List<Integer> newFish = FishUtil.incrementFishIntegers(fishSubListCopy)
            fishSubListCopy = newFish
        }
        println("Iterated over fishMap index: " + index + " ... size: " + fishSubListCopy.size())
        interimCounter += fishSubListCopy.size()
    }
    println("After  day " + 256 + ": (" + interimCounter + ")")

    // A different approach would be to do the calculations only once
    // for a fish of initial count 1 (there are lots of them)
    // Then, just multiply the counts by the number of times the initial count
    // appears in the initial list

}

