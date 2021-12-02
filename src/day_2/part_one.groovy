package day_2

static void main(String[] args) {
    println("Submarine Movement")
    File file = new File("./input.txt")
    List<String> lines = file.readLines()
    List<Instruction> instructions = lines.collect { String line -> new Instruction(line) }
    // Store the travel history in a series of X (item1) and Y (item2) coordinates
    List<Tuple2<Integer, Integer>> history = new ArrayList<>()
    history.add(new Tuple2<>(0, 0))
    instructions.eachWithIndex {
        Instruction instruction, int index -> {
            Tuple2<Integer, Integer> position = history.get(index)
            switch (instruction.direction) {
                case Direction.forward:
                    // increment x, y (depth) remains the same 
                    history.add(new Tuple2<Integer, Integer>(
                        position.getV1() + instruction.increment,
                        position.getV2()))
                    break
                case Direction.up:
                    // x remains the same, decrement y (i.e., we're decreasing depth) 
                    history.add(new Tuple2<Integer, Integer>(
                        position.getV1(),
                        position.getV2() - instruction.increment))
                    break
                case Direction.down:
                    // x remains the same, increment y (i.e., we're increasing depth) 
                    history.add(new Tuple2<Integer, Integer>(
                        position.getV1(),
                        position.getV2() + instruction.increment))
                    break
                default:
                    break
            }
        }
    }
    history.eachWithIndex {
        Tuple2<Integer, Integer> position, int i -> {
            if (i == 0) {
                println("Starting at 0:0")
            } else {
                Instruction instruction = instructions.get(i-1)
                println(instruction.toString().padRight(15) + (position.getV1() + ":" + position.getV2()).center(11) )
            }
        }
    }
    println("Last position multiplied: " + (history.last().getV1() * history.last().getV2()))
}

enum Direction { 
    forward, up, down
    static Direction parse(String value) {
        values().find { direction -> direction.name().equalsIgnoreCase(value) }
    }
}

class Instruction {
    Direction direction
    Integer increment
    Instruction(String line) {
        String[] parts = line.split()
        direction = Direction.parse(parts[0])
        increment = Integer.valueOf(parts[1])
    }
    String toString() {
        "[" + direction.name() + ": " + increment + "]" 
    }
}