package day_7

class Crabs {
    static void main(String[] args) {
        boolean train = false
        boolean partOne = false
        println("Crab Positions")
        File file = train ? new File("./src/day_7/train.txt") : new File("./src/day_7/input.txt")
        List<Integer> crabPositions = file
                .readLines()
                .get(0)
                .collect {line -> line.split(",")}
                .collect {strings -> strings.collect {it -> Integer.valueOf(it)}}
                .flatten() as List<Integer>
        println(crabPositions)

        // Calculate the fuel cost of moving each
        // crab to each of the possible positions.
        // With that data, we can find the min value
        // in the position to fuel cost map
        Range<Integer> positionRange = crabPositions.min()..crabPositions.max()
        Map<Integer, Integer> positionToFuelCostMap = new HashMap<>()
        positionRange.each {destination ->
            int[] fuelCosts = crabPositions.collect { crabPosition -> {
                partOne ?
                        partOneFuelCost(crabPosition, destination) :
                        partTwoFuelCost(crabPosition, destination)
            }} as int[]
            positionToFuelCostMap.put(destination, fuelCosts.sum())
        }
        int max = positionToFuelCostMap.values().max()
        def maxEntries = positionToFuelCostMap.findAll {it.value == max }
        println("Max Entries: ")
        maxEntries.each {println(it)}
        int min = positionToFuelCostMap.values().min()
        def minEntries = positionToFuelCostMap.findAll {it.value == min }
        println("Min Entries: ")
        minEntries.each {println(it)}
    }

    static int partOneFuelCost(Integer crabPosition, Integer position) {
        Math.abs(crabPosition - position)
    }

    static int partTwoFuelCost(Integer crabPosition, Integer position) {
        fact(Math.abs(crabPosition - position))
    }

    static int fact(int n) {
        n == 0 ? 0 : n + fact(n - 1)
    }
}




