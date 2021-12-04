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

    static final int findLeastCommonBit(int[] bitInts) {
        Map<Integer, Integer> bitToCountMap = new HashMap<>()
        bitInts.each {
            Integer bit ->
                {
                    bitToCountMap.putIfAbsent(bit, 0)
                    bitToCountMap.put(bit, bitToCountMap.get(bit) + 1)
                }
        }
        int leastVal = bitToCountMap.values().sort().first()
        bitToCountMap.find { it.value == leastVal }?.getKey()
    }

    static final int[][] fileToBitMatrix(String path) {
        File file = new File(path)
        List<String> lines = file.readLines()
        int cols = lines[0].length()
        int rows = lines.size()
        int[][] bitMatrix = new int[rows][cols]
        lines.eachWithIndex {
            String line, int index ->
                {
                    bitMatrix[index] = lineStringToIntBits(line)
                }
        }
        bitMatrix
    }
    
    static final int[] lineStringToIntBits(String line) {
        line.toCharArray().collect { Character it -> Integer.valueOf(String.valueOf(it)) }
    }
    
    static final List<String> filterRowsOnPosition(List<String> rows, int position, int bit) {
    rows.findAll { 
        String row -> {
            row.substring(position).startsWith(String.valueOf(bit))
        } 
    }
}
}
