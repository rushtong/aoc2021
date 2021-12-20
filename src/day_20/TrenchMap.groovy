package day_20

import groovy.transform.TailRecursive

class TrenchMap {

    /**
     https://adventofcode.com/2021/day/20

     ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##
     #..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###
     .######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.
     .#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....
     .#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..
     ...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....
     ..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

     #..#.
     #....
     ##..#
     ..#..
     ..###

     The first section is the image enhancement algorithm. It is normally given on a single line, but it
     has been wrapped to multiple lines in this example for legibility. The second section is the input
     image, a two-dimensional grid of light pixels (#) and dark pixels (.).

     * @param args
     */

    private static String algorithm = // new File("./src/day_20/algorithm.txt").readLines().get(0)
            "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
            "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
            ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
            ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
            ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
            "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
            "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#"
    private static String input =
            "#..#.\n" +
            "#....\n" +
            "##..#\n" +
            "..#..\n" +
            "..###\n"

    // 5785, 6225 are too high
    static void main(String[] args) {
        println(getKeyMap().size())
//        String[][] image = new File("./src/day_20/input.txt").readLines().each {it.split("")}
        String[][] image = input.split("\\n").collect {it.split("")}
        String[][] enhanced = enhanceImageBySteps(image, 2)
        def hashes = enhanced.collect {row ->
            row.findAll {cell -> (cell == "#") }
        }.flatten()
        println(hashes.size())
    }

    @TailRecursive
    private static String[][] enhanceImageBySteps(String[][] image, int step) {
        String[][] paddedImage = padImage(image)
        println()
        paddedImage.each {println(it.join(""))}
        if (step < 1) {
            return paddedImage
        }
        return enhanceImageBySteps(enhanceImage(paddedImage), step - 1)
    }

    private static String[][] enhanceImage(String[][] image) {
        Map<Integer, String> keyMap = getKeyMap()
        String[][] enhancedImage = new String[image.length][image[0].size()]
        image.eachWithIndex { String[] row, int rowIndex ->
            row.eachWithIndex{ String cell, int colIndex ->
                String[][] window = findWindowsForCell(image, rowIndex, colIndex)
                int decimalVal = convertToDecimal(window)
                String newPixel = keyMap.get(decimalVal)
                enhancedImage[rowIndex][colIndex] = newPixel
            }
        }
        enhancedImage
    }

    private static Integer convertToDecimal(String[][] window) {
        String pixels = window.collect{it.join("")}.join("")
        pixels = pixels
                .replaceAll("\\.", "0")
                .replaceAll("#", "1")
        Integer.parseInt(pixels, 2)
    }

    private static String[][] findWindowsForCell(String[][] image, int x, int y) {
        String[][] window = new String[3][3]
        (x-1..x+1).eachWithIndex {row, windowRowIndex ->
            (y-1..y+1).eachWithIndex { col, windowColIndex ->
                // Can't rely on negative index out of bounds, Groovy counts backwards from end of array
                String cell = (row < 0 || col < 0 || row >= image.length || col >= image[0].size()) ? "." : image[row][col]
                window[windowRowIndex][windowColIndex] = cell
            }
        }
        window
    }

    // Pad image out two places in all directions
    private static String[][] padImage(String[][] image) {
        List<String[]> lines = image.collect()
        String[] newRow = ("." * lines[0].size()).split("")
        lines.add(0, newRow)
        lines.add(0, newRow)
        lines.add(newRow)
        lines.add(newRow)
        lines.collect {line ->
            (".." + line.join("") + "..").split("")
        }.toArray()
    }

    private static Map<Integer, String> getKeyMap() {
        Map<Integer, String> keyMap = new HashMap<>()
        algorithm.split("").eachWithIndex { String entry, int i -> keyMap.put(i, entry)}
        keyMap
    }
}
