package day_10

import groovy.transform.TailRecursive

class Syntax {

    static void main(String[] args) {
        boolean train = false
        boolean partOne = true // Part 1: 296535
        println("Syntax Scoring")
        File file = train ? new File("./src/day_10/train.txt") : new File("./src/day_10/input.txt")

        List<String> rightVals = pairMap().values().toList()
        List<Integer> scores = file.readLines().collect {line ->
            String filteredLine = filterMatches(line)
            boolean illegal = rightVals.any{filteredLine.contains(it)} 
            if (illegal) {
                List<String> filteredBrackets = filteredLine.split("")
                int index = filteredBrackets.findIndexOf {rightVals.contains(it) }
                println()
                println(line)
                println(filteredLine)
                println("Expected " + filteredBrackets[index-1] + " but found " + filteredBrackets[index] + " instead")
                println("Points: " + scoreMap().get(filteredBrackets[index]))
                scoreMap().get(filteredBrackets[index])
            } else {
                0
            }
        }
        println("Final Tally: " + scores.sum())
    }
    
    static Map<String, Integer> scoreMap() {
        [")":3, "]":57, "}": 1197, ">": 25137]
    }
    
    static Map<String, String> pairMap() {
        ["{":"}", "(":")", "[":"]", "<":">"]
    }
    
    static List<String> pairStrings() {
        ["{}", "()", "[]", "<>"]
    }
    
    static List<String> pairRegexes() {
        ["\\{\\}", "\\(\\)", "\\[\\]", "\\<\\>"]
    }

    @TailRecursive
    static String filterMatches(String line) {
        boolean match = pairStrings().any {pair -> line.contains(pair)}
        if (!match) {
            return line
        }
        pairRegexes().each {regex -> line = line.replaceAll(regex, "")}
        return filterMatches(line)
    }
}
