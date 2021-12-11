package day_3

import groovy.transform.TailRecursive

class LifeSupport {

    static void main(String[] args) {
        boolean train = false
        println("Submarine Life Support")
        File file = train ? new File("./src/day_3/train.txt") : new File("./src/day_3/bits.txt")
        List<String> lines = file.readLines()
        String mcb = filterLines(true, 0, lines)
        String lcb = filterLines(false, 0, lines)
        println("O2 : " + mcb + "; decimal: " + Integer.parseInt(mcb, 2))
        println("CO2: " + lcb + "; decimal: " + Integer.parseInt(lcb, 2))
        println("Rating: " + Integer.parseInt(mcb, 2) * Integer.parseInt(lcb, 2))
    }

    @TailRecursive
    static String filterLines(boolean mostCommon, int filterColumn, List<String> lines) {
        if (lines.size() == 1 || filterColumn > lines[0].size()) {
            return lines[0]
        }

        // find mcb/lcb bit of column in question
        List<Integer> column = getColumnBits(filterColumn, lines)
        Integer mcbInt = mcb(column)
        Integer lcbInt = lcb(column)
        int mcb_lcb = mostCommon ? mcbInt : lcbInt

        // If they're equally common, use the most common
        if (mcbInt == lcbInt) {
            mcb_lcb = mostCommon ? 1 : 0
        }
        List<String> filteredLines = filterLinesOnColumnBit(mcb_lcb, filterColumn, lines)
        filterLines(mostCommon, filterColumn + 1, filteredLines)
    }

    static List<Integer> getColumnBits(int filterColumn, List<String> lines) {
        lines.collect { line ->
            Integer.valueOf(line.split("")[filterColumn])
        }
    }

    static List<String> filterLinesOnColumnBit(int mcb_lcb, int filterColumn, List<String> lines) {
        lines.findAll {line ->
            line.split("")[filterColumn] == String.valueOf(mcb_lcb)
        }
    }

    static int mcb(List<Integer> bits) {
        def zeroCount = bits.count(0)
        def oneCount = bits.count(1)
        zeroCount > oneCount ? 0 : 1
    }

    static int lcb(List<Integer> bits) {
        def zeroCount = bits.count(0)
        def oneCount = bits.count(1)
        zeroCount < oneCount ? 0 : 1
    }
}
