package day_3

static void main(String[] args) {
    println("Submarine Power Consumption")
    // Create a 2D array so we can iterate over rows and columns
    int[][] bitMatrix = Utils.fileToBitMatrix("./bits.txt")
    // Transpose the matrix (turn columns into rows) so we can operate on the columns one at a time as a row
    int[][] transposed = bitMatrix.transpose()
    int[] gammaBits = transposed.collect { Utils.findMostCommonBit(it)}
    // Epsilon bits are just the flipped version of Gamma bits
    int[] epsilonBits = gammaBits.collect { it == 0 ? 1 : 0 } as int[]
    Integer gamma = Utils.convertBitArrayToDecimal(gammaBits)
    Integer epsilon = Utils.convertBitArrayToDecimal(epsilonBits)
    println("Gamma Rating:      " + gamma)
    println("Epsilon Rating:    " + epsilon)
    println("Power Consumption: " + gamma * epsilon)
}
