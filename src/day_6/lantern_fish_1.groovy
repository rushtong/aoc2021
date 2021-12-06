package day_6


static void main(String[] args) {
    println("Lantern Fish Part 1")
    File file = new File("./input.txt")
    List<Fish> fish = file.readLines().get(0).split(',').collect {new Fish(Integer.valueOf(it))}
    println("Initial State (" + fish.size() + "): " + fish)

    def range = 1..256
    range.each {day ->
        List<Fish> newFish = FishUtil.incrementFish(fish)
        println("After " + day + " day: (" + newFish.size() + ")")// + newFish)
        fish = newFish
    }

}

class FishUtil {
    static List<Fish> incrementFish(List<Fish> fish) {
        fish.collect {it.spawn() }.flatten()
    }
}

class Fish {
    int spawnTimer

    Fish(int spawnTimer) {
        this.spawnTimer = spawnTimer
    }

    List<Fish> spawn() {
        if (getSpawnTimer() == 0) {
            // Return itself, reset to 6, and a new fish with a spawn timer of 8
            [new Fish(6), new Fish(8)]
        } else {
            [new Fish(getSpawnTimer() - 1)]
        }
    }

    String toString() {
        String.valueOf(getSpawnTimer())
    }

}