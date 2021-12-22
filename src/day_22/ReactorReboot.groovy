package day_22

class ReactorReboot {

    static void main(String[] args) {
        Map<Coordinate, Boolean> cubeState = generateCubes()
        println("All Cubes: " + cubeState.size())
        println("Active Cubes: " + cubeState.values().findAll { it }.size())
        parseInstructions("./src/day_22/input.txt").eachWithIndex { Instruction i, int index ->
            Map<Coordinate, Boolean> newCubeState = applyInstruction(cubeState, i)
            println("Active Cubes for Step " + index + ": " + newCubeState.values().findAll { it }.size())
        }
    }

    static Map<Coordinate, Boolean> applyInstruction(Map<Coordinate, Boolean> cubeState, Instruction i) {
        i.zRange.each {z ->
            i.yRange.each {y ->
                i.xRange.each {x ->
                    Coordinate c = new Coordinate(x, y, z)
                    if (cubeState.containsKey(c)) {
                        cubeState.put(c, i.onOff)
                    }
                }
            }
        }
        cubeState
    }

    static List<Instruction> parseInstructions(String path) {
        List<Instruction> instructions = new ArrayList<>()
        new File(path).readLines().each { line ->
            String[] parts = line.split(" ")
            Instruction i = new Instruction()
            i.onOff = (parts[0] == "on")
            String[] ranges = parts[1].split(",")
            List<Integer> xBoundaries = ranges[0].replaceFirst("x=", "").split("\\.\\.").collect {Integer.valueOf(it)}
            List<Integer> yBoundaries = ranges[1].replaceFirst("y=", "").split("\\.\\.").collect {Integer.valueOf(it)}
            List<Integer> zBoundaries = ranges[2].replaceFirst("z=", "").split("\\.\\.").collect {Integer.valueOf(it)}

            i.xRange = (xBoundaries[0]..xBoundaries[1])
            i.yRange = (yBoundaries[0]..yBoundaries[1])
            i.zRange = (zBoundaries[0]..zBoundaries[1])

            // If any range is outside of the boundaries, we can ignore it
            if ((i.xRange.min() > -50 && i.xRange.max() <= 50) &&
                (i.yRange.min() > -50 && i.yRange.max() <= 50) &&
                (i.zRange.min() > -50 && i.zRange.max() <= 50)
                ) {
                instructions.add(i)
            }
        }
        instructions
    }

    static class Instruction {
        Range<Integer> xRange
        Range<Integer> yRange
        Range<Integer> zRange
        Boolean onOff
    }

    static Map<Coordinate, Boolean> generateCubes() {
        Map<Coordinate, Boolean> cubeState = new HashMap<>()
        (-50..50).each{ int z ->
            (-50..50).each{ int y ->
                (-50..50).each {int x ->
                    cubeState.put(new Coordinate(x, y, z), false)
                }
            }
        }
        cubeState
    }

    static class Coordinate {
        int x
        int y
        int z
        Coordinate(int x, int y, int z) {
            this.x = x
            this.y = y
            this.z = z
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (!(o instanceof Coordinate)) return false

            Coordinate that = (Coordinate) o

            if (x != that.x) return false
            if (y != that.y) return false
            if (z != that.z) return false

            return true
        }

        int hashCode() {
            int result
            result = x
            result = 31 * result + y
            result = 31 * result + z
            return result
        }
    }
}
