package day_6

// Day 80 is (366057) for this dataset
class LanternFish {
    static void main(String[] args) {
        boolean train = false

        println("Lantern Fish Part 1")
        File file = train ? new File("./src/day_6/train.txt") : new File("./src/day_6/input.txt")
        List<Integer> fish = file.readLines().get(0).split(',').collect {Integer.valueOf(it)}
        println("Initial State (" + fish.size() + "): " + fish)

        def range = 1..80
        range.each {day ->
            List<Integer> newFish = incrementFish(fish)
            println("After  day " + day + ": (" + newFish.size() + ")")
            fish = newFish
        }
    }

    static List<Integer> incrementFish(List<Integer> fish) {
        fish.collect {spawn(it) }.flatten() as List<Integer>
    }

    static List<Integer> spawn(Integer spawnRate) {
        spawnRate == 0 ? [6, 8] : [spawnRate - 1]
    }
}
