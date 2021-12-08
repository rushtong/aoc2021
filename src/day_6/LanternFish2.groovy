package day_6

import groovy.transform.CompileStatic
import groovy.transform.TailRecursive
import groovy.transform.TypeChecked

@CompileStatic
@TypeChecked
class LanternFish2 {
    static void main(String[] args) {
        boolean train = true

        println("Lantern Fish Part 2")
        File file = train ? new File("./src/day_6/train.txt") : new File("./src/day_6/input.txt")
        Collection<Integer> fish = file.readLines().get(0).split(',').collect {Integer.valueOf(it) }
        println("Initial State (" + fish.size() + "): " + fish)

        // Do the traversal calculations only once for initial count 1 (there are lots of them)
        // Calculate the value for anything above that manually (2 is just 1 more than the count for 1, etc)
        // Lastly, sum up the matching count for each value in the input file for a total

// ➜  export JAVA_OPTS="$JAVA_OPTS -Xmx8000M" gets me to 205-ish

// ➜  aoc2021 git:(main) ✗ export JAVA_OPTS="$JAVA_OPTS -Xmx10000M" groovy ./src/day_6/LanternFish2.groovy
// ➜  Gets me to around ... 226-ish and we hit OOM

// ➜  aoc2021 git:(main) ✗ export JAVA_OPTS="$JAVA_OPTS -Xmx14000M" groovy ./src/day_6/LanternFish2.groovy
// ➜  Gets me to around ... 229-ish, insanely slow

// ➜  aoc2021 git:(main) ✗ export JAVA_OPTS="$JAVA_OPTS -Xmx56000M" groovy ./src/day_6/LanternFish2.groovy
// ➜  Gets me to around ... 240 after a couple hours

// ➜  aoc2021 git:(main) ✗ export JAVA_OPTS="$JAVA_OPTS -Xmx150000M" groovy ./src/day_6/LanternFish2.groovy
// ➜  Gets me to around ... 244 before OOM

        Map<Integer, Integer> spawnStateMap = new HashMap<>()
        def spawnState1 = spawnFactorial(256, [1])
        // Spawn rates of anything more than 1 is just one additional prepended array element
        spawnStateMap.put(1, spawnState1.size())
        spawnStateMap.put(2, spawnState1.size() + 1)
        spawnStateMap.put(3, spawnState1.size() + 2)
        spawnStateMap.put(4, spawnState1.size() + 3)
        spawnStateMap.put(5, spawnState1.size() + 4)
        // There are no input values over 5

        println(spawnStateMap)
    }

    @TailRecursive
    static List<Integer> spawnFactorial(int day, List<Integer> accumulator) {
        println("Day: " + (257 - day))
        day == 1 ?
                accumulator :
                spawnFactorial(day - 1, accumulator.collect{spawnMap.get(it)}.flatten() as List<Integer>)
    }

    // Testing if this is quicker than the calculation version ... it does a little bit!
    static final Map<Integer, List<Integer>> spawnMap = [
            0: [6,8], 1: [0], 2: [1], 3: [2], 4: [3],
            5: [4],   6: [5], 7: [6], 8: [7], 9: [8]]

}
