package day_7

class Crabs {
    static void main(String[] args) {
        println("Crab Positions")
//        File file = new File("./src/day_7/train.txt")
        File file = new File("./src/day_7/input.txt")
        List<Integer> crabPositions = file
                .readLines()
                .collect {line -> line.split(",")}
                .collect {strings -> strings.collect {it -> Integer.valueOf(it)}}
                .flatten()
        println(crabPositions)

        // Try to calculate the fuel cost of moving all
        // crabs to each of the possible positions.
        // With that data, we can find the min value
        // in the position to fuel cost map
        Range<Integer> positionRange = crabPositions.min()..crabPositions.max()
        Map<Integer, Integer> positionToFuelCostMap = new HashMap<>()
        positionRange.each {position ->
            int[] fuelCosts = crabPositions.collect {crabPosition -> {
                partOneFuelCost(crabPosition, position)
            }}
            positionToFuelCostMap.put(position, fuelCosts.sum())
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
}




