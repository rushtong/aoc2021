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

//  export JAVA_OPTS="$JAVA_OPTS -Xmx8000M" gets me to 205-ish
// ➜  aoc2021 git:(main) ✗ export JAVA_OPTS="$JAVA_OPTS -Xmx10000M" groovy ./src/day_6/LanternFish2.groovy
// ➜  Gets me to around ...
        Map<Integer, Integer> spawnStateMap = new HashMap<>()
        def spawnState1 = spawnFactorial(256, [1])
//        // Spawn rates of anything more than 1 is just one additional prepended array element
        spawnStateMap.put(1, spawnState1.size())
        spawnStateMap.put(2, spawnState1.size() + 1)
        spawnStateMap.put(3, spawnState1.size() + 2)
        spawnStateMap.put(4, spawnState1.size() + 3)
        spawnStateMap.put(5, spawnState1.size() + 4)
//        // After 6, there are 2 additional prepended array elements since that's when we spawn
        spawnStateMap.put(6, spawnState1.size() + 8)
        spawnStateMap.put(7, spawnState1.size() + 9)
        spawnStateMap.put(8, spawnState1.size() + 10)
        spawnStateMap.put(9, spawnState1.size() + 11)

        println(spawnStateMap)
    }

    @TailRecursive
    static List<Integer> spawnFactorial(int day, List<Integer> accumulator) {
        println("Day: " + day)
        day == 1 ?
                accumulator :
                spawnFactorial(day - 1, accumulator.collect {spawn(it)}.flatten() as List<Integer>)
    }

    static List<Integer> spawn(int n) {
        n == 0 ? [6, 8] : [n - 1]
    }

}
