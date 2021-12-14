package day_14

class Polymerization {

    static void main(String[] args) {
        println("Extended Polymerization")
        boolean train = true
        File file = train ? new File("./src/day_14/train.txt") : new File("./src/day_14/input.txt")
        List<String> lines = file.readLines()
        lines.each { println(it) }
    }

}
