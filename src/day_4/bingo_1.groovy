package day_4

static void main(String[] args) {
    println("Submarine Life Support")
    File file = new File("./input.txt")
    List<String> lines = file.readLines()
    List<Integer> draws = lines.get(0).split(",").collect {String it ->  Integer.valueOf(it) }
    List<Board> boards = lines.drop(1).findAll {String it -> it.length() > 0 }.collate(5).collect {List<String> it -> new Board(it)}
    boards.each {Board it -> println(it.grid) }
}

class Board {
    int[][] grid
    Board(List<String> lines) {
        grid = new int[5][5]
        lines.eachWithIndex { 
            String line, int index -> {
                grid[index] = line.split().collect { String it -> Integer.valueOf(it) }
            }
        }
    }
}