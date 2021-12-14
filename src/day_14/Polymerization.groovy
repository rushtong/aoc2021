package day_14

import groovy.transform.TailRecursive

class Polymerization {

    static void main(String[] args) {
        println("Extended Polymerization")
        boolean train = false
        File file = train ? new File("./src/day_14/train.txt") : new File("./src/day_14/input.txt")
        List<String> lines = file.readLines()
        String start = lines.pop()
        lines.pop()
        Map<String, String> transformRules = new HashMap<>()
        lines.each {line ->
            String[] parts = line.split(" -> ")
            def key = parts[0]
            def keyChars = key.split("")
            def transform = keyChars[0] + parts[1]
            transformRules.put(key, transform)
        }
        println(transformRules)
        println(start)
        String polymer = iterativeTransform(start, transformRules, 10)
        def grouping = polymer.split("").groupBy {it}
        grouping.each {
            println(it.key + ": " + it.value.size())
        }

    }

    @TailRecursive
    static String iterativeTransform(String base, Map<String, String> rules, int step) {
        if (step == 0) {
            return base
        }
        List<String> pairs = applyTransform(base, rules)
        iterativeTransform(pairs.join(""), rules, step - 1)
    }

    static List<String> applyTransform(String base, Map<String, String> rules) {
        List<String> pairs = splitIntoPairs(base)
        List<String> mappedPairs = pairs.collect {pairString ->
            if (rules.containsKey(pairString)) {
                rules.get(pairString)
            } else {
                pairString
            }
        }
        mappedPairs
    }

    static List<String> splitIntoPairs(String str) {
        List<String> pairs = new ArrayList<>()
        List<String> letters = str.split("")
        letters.eachWithIndex { String letter, int i ->
            if (i < letters.size() - 1) {
                pairs.add([letter, letters[i+1]].join(""))
            } else {
                pairs.add(letter)
            }
        }
        pairs
    }

}
