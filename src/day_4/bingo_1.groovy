package day_4

static void main(String[] args) {
    println("Submarine Life Support")
    File file = new File("./input.txt")
    List<String> lines = file.readLines()
    List<Integer> draws = lines.get(0).split(",").collect { String it ->  Integer.valueOf(it) }
    List<Board> boards = lines.drop(1).findAll { String it -> it.length() > 0 }.collate(5).collect { List<String> it -> new Board(it)}
    boards.sort { Board it -> it.winningPosition(draws) }
    boards.reverse().each { Board it -> { it.debug(draws) }}
}

class Board {
    List<List<Integer>> grid
    Board(List<String> lines) {
        grid = new ArrayList<>()
        lines.each { 
            String line -> {
                grid.add(line.split().collect { String it -> Integer.valueOf(it) }) 
            }
        }
    }
    int winningPosition(List<Integer> draws) {
        List<List<Integer>> winningRuns = grid + grid.transpose()
        def range = 0..draws.size()
        int position = range.find { int index -> {
            def subList = draws.subList(0, index)
            winningRuns.any {
                List<Integer> run ->
                    {
                        subList.containsAll(run)
                    }
            }
        }}
        position
    }
    int score(List<Integer> draws) {
        List<Integer> markedValues = draws.subList(0, winningPosition(draws))
        List<List<Integer>> modifiedGrid = grid.collect { it.collect {markedValues.contains(it) ? 0 : it}}
        modifiedGrid.collect { List<Integer> it -> it.sum() }.sum()
    }
    void debug(List<Integer> draws) {
        int winningPosition = winningPosition(draws)
        int score = score(draws)
        def winningDraws = draws.subList(0, winningPosition)
        def winningCall = draws.subList(0, winningPosition).last()
        def markedGrid = grid.collect { it.collect {winningDraws.contains(it) ? 0 : it}}
        println("------------------------------------------------------")
        println("Board:         " + grid[0])
        println("               " + grid[1])
        println("               " + grid[2])
        println("               " + grid[3])
        println("               " + grid[4])
        println("Marked Board:  " + markedGrid[0])
        println("               " + markedGrid[1])
        println("               " + markedGrid[2])
        println("               " + markedGrid[3])
        println("               " + markedGrid[4])
        println("Winning Draws: " + winningDraws)
        println("Position:      " + winningPosition)
        println("Score:         " + score)
        println("Winning Call:  " + winningCall)
        println("Final Score:   " + score * winningCall)
    }
}