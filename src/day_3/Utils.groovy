package day_3

class Utils {
    static final Integer convertBitArrayToDecimal(int[] bitInts) {
        String binaryAsString = bitInts.collect { String.valueOf(it) }.join("")
        Integer.parseInt(binaryAsString, 2)
    }

    static final int findMostCommonBit(int[] bitInts) {
        Map<Integer, Integer> bitToCountMap = new HashMap<>()
        bitInts.each {
            Integer bit ->
                {
                    bitToCountMap.putIfAbsent(bit, 0)
                    bitToCountMap.put(bit, bitToCountMap.get(bit) + 1)
                }
        }
        int maxVal = bitToCountMap.values().sort().last()
        bitToCountMap.find { it.value == maxVal }?.getKey()
    }

    static final int[][] fileToBitMatrix(String path) {
        File file = new File(path)
        List<String> lines = file.readLines()
        int cols = lines[0].chars().collect().size()
        int rows = lines.size()
        int[][] bitMatrix = new int[rows][cols]
        lines.eachWithIndex {
            String line, int index ->
                {
                    bitMatrix[index] = line.toCharArray().collect { Character it -> Integer.valueOf(String.valueOf(it)) }
                }
        }
        bitMatrix
    }
}
