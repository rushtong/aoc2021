package day_6

// Part 1 runs out of heap space around 129
// List based approach does slightly better

static void main(String[] args) {
    println("Lantern Fish Part 2")
    File file = new File("./input.txt")
    Collection<Integer> fish = file.readLines().get(0).split(',').collect {Integer.valueOf(it) }
    println("Initial State (" + fish.size() + "): " + fish)

    def range = 1..256
    range.each {day ->
        List<Integer> newFish = FishUtil.incrementFishIntegers(fish)
        println("After  day " + day + ": (" + newFish.size() + ")")
        fish = newFish
    }

}

