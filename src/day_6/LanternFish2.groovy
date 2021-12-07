package day_6

// OOM's around 184 with this version
class LanternFish2 {
    static void main(String[] args) {
        boolean train = true

        println("Lantern Fish Part 2")
        File file = train ? new File("./src/day_6/train.txt") : new File("./src/day_6/input.txt")
        Collection<Integer> fish = file.readLines().get(0).split(',').collect {Integer.valueOf(it) }
        println("Initial State (" + fish.size() + "): " + fish)

        // Do the calculations only once for a fish of initial count 1 (there are lots of them)
        // Then, just multiply the counts by the number of times the initial count
        // appears in the initial list

        Map<Integer, Integer> spawnStateMap = new HashMap<>()
        def intRange = 1..fish.max()
        intRange.each {spawnState ->
            def tempState = spawnFactorial(256, [spawnState])
            spawnStateMap.put(spawnState, tempState.size())
        }
        println(spawnStateMap)
    }

    static List<Integer> spawnFactorial(int day, List<Integer> accumulator) {
        println("Day: " + (256 - day + 1) + " size:" + accumulator.size())
        day == 1 ?
                accumulator :
                spawnFactorial(day - 1, accumulator.collect {spawn(it)}.flatten() as List<Integer>)
    }

    static int[] spawn(int n) {
        n == 0 ? [6, 8] : [n - 1]
    }

}
