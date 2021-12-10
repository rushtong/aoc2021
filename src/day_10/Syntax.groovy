package day_10

import groovy.transform.TailRecursive

class Syntax {

    static void main(String[] args) {
        boolean train = false
        boolean partOne = false // Part 1: 296535 
                                // Part 2: 4245130838
        println("Syntax Scoring")
        File file = train ? new File("./src/day_10/train.txt") : new File("./src/day_10/input.txt")

        List<String> rightVals = pairMap().values().toList()
        if (partOne) {
            List<Integer> scores = file.readLines().collect { line ->
                String filteredLine = filterMatches(line)
                boolean illegal = rightVals.any { filteredLine.contains(it) }
                if (illegal) {
                    List<String> filteredBrackets = filteredLine.split("")
                    int index = filteredBrackets.findIndexOf { rightVals.contains(it) }
                    println()
                    println(line)
                    println(filteredLine)
                    println("Expected " + filteredBrackets[index - 1] + " but found " + filteredBrackets[index] + " instead")
                    println("Points: " + scoreMap().get(filteredBrackets[index]))
                    scoreMap().get(filteredBrackets[index])
                } else {
                    0
                }
            }
            println("Final Tally: " + scores.sum())
        } else {
            List<Long> scores = file
                .readLines().collect { line ->
                    String filteredLine = filterMatches(line)
                    boolean illegal = rightVals.any { filteredLine.contains(it) }
                    if (!illegal) {
                        println()
                        println(filteredLine)
                        String complement = filteredLine.collect { it -> pairMap().get(it)}.reverse().join("")
                        println(complement)
                        Long score
                        score = autocompleteScoreForComplement(complement, 0)
                        println(score)
                        score
                    } else {
                        0
                    }
                }
                .findAll {it > 0}
                .sort()
            println(scores)
            // Always an odd number of values, per problem statement
            BigDecimal middle = (scores.size()/2).trunc()
            println(middle)
            println("Middle Value: " + scores[middle])
        }
    }
    
    @TailRecursive
    static Long autocompleteScoreForComplement(String complement, Long score) {
        if (complement.isEmpty()) {
            return score
        }
        List<String> parts = complement.split("")
        String firstChar = parts.pop()
        Long partScore = autocompleteScoreMap().get(firstChar)
        autocompleteScoreForComplement(parts.join(""), score * 5 + partScore)
    }

    static Map<String, Integer> autocompleteScoreMap() {
        [")": 1, "]": 2, "}": 3, ">": 4]
    }

    static Map<String, Integer> scoreMap() {
        [")": 3, "]": 57, "}": 1197, ">": 25137]
    }

    static Map<String, String> pairMap() {
        ["{": "}", "(": ")", "[": "]", "<": ">"]
    }

    static List<String> pairStrings() {
        ["{}", "()", "[]", "<>"]
    }

    static List<String> pairRegexes() {
        ["\\{\\}", "\\(\\)", "\\[\\]", "\\<\\>"]
    }

    @TailRecursive
    static String filterMatches(String line) {
        boolean match = pairStrings().any { pair -> line.contains(pair) }
        if (!match) {
            return line
        }
        pairRegexes().each { regex -> line = line.replaceAll(regex, "") }
        return filterMatches(line)
    }
}
