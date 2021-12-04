package day_3

static void main(String[] args) {
    println("Submarine Life Support")
    // Create a 2D array so we can iterate over rows and columns
    File file = new File("./bits.txt")
    List<String> rows = file.readLines()
    List<String> o2Vals = filterRows(rows, 0, true)
    println(o2Vals)

}

private static List<String> filterRows(List<String> rows, int position, boolean mostCommon) {
    if (rows.size() == 1) {
        return rows
    }
    int[][] bitMatrix = new int[rows.size()][rows.get(0).length()]
    rows.eachWithIndex {
        String line, int index ->
            {
                bitMatrix[index] = Utils.lineStringToIntBits(line)
            }
    }
    int[][] transposed = bitMatrix.transpose()
    println(transposed)
    for (String row in rows) {
        int[] lineBits = transposed[position]
        int leastCommonBit = Utils.findLeastCommonBit(lineBits)
        int mostCommonBit = Utils.findMostCommonBit(lineBits)
        // if the counts are the same, then we move on to the next position 
        if (leastCommonBit == mostCommonBit) {
            println("found a tie, moving on to next position")
            return filterRows(rows, position + 1, mostCommon)
        }
        int filterBit = mostCommon ? mostCommonBit : leastCommonBit
        println("row: " + row + " position: " + position + " filterBit: " + filterBit)
        List<String> filteredRows = Utils.filterRowsOnPosition(rows, position, filterBit)
        println("filteredRows: " + filteredRows)
        if (filteredRows.size() == 1) {
            return filteredRows
        }
        return filterRows(filteredRows, position + 1, mostCommon)
    }
}
