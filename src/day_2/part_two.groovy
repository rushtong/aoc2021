package day_2

static void main(String[] args) {
    println("Aimed Submarine Movement")
    File file = new File("./input.txt")
    List<String> lines = file.readLines()
    List<Instruction> instructions = lines.collect { String line -> new Instruction(line) }
    // Store the travel history in a series of X (item1) and Y (item2) coordinates 
    // alongside an AIM setting (item3)
    List<Tuple3<Integer, Integer, Integer>> history = new ArrayList<>()
    history.add(new Tuple3<>(0, 0, 0))
    instructions.eachWithIndex {
        Instruction instruction, int index -> {
            Tuple3<Integer, Integer, Integer> position = history.get(index)
            switch (instruction.direction) {
                case Direction.forward:
                    // increment x, y (depth) is modified by aim x increment
                    int depthChange = instruction.increment * position.getV3()
                    history.add(new Tuple3<Integer, Integer, Integer>(
                        position.getV1() + instruction.increment,
                        position.getV2() + depthChange,
                        position.getV3()))
                    break
                case Direction.up:
                    history.add(new Tuple3<Integer, Integer, Integer>(
                        position.getV1(),
                        position.getV2(),
                        position.getV3() - instruction.increment))
                    break
                case Direction.down:
                    history.add(new Tuple3<Integer, Integer, Integer>(
                        position.getV1(),
                        position.getV2(),
                        position.getV3() + instruction.increment))
                    break
                default:
                    break
            }
        }
    }
    history.eachWithIndex {
        Tuple3<Integer, Integer, Integer> position, int i -> {
            if (i == 0) {
                println("Starting at 0:0")
            } else {
                Instruction instruction = instructions.get(i-1)
                println(instruction.toString().padRight(15) + (position.getV1() + ":" + position.getV2()).center(20) + ("[aim:" + position.getV3() + "]").center(10))
            }
        }
    }
    println("Last position multiplied: " + (history.last().getV1() * history.last().getV2()))
}
