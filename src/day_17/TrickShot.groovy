package day_17

import com.sun.tools.javac.util.Pair
import groovy.transform.TailRecursive

class TrickShot {

    /**
     *
     The probe's x,y position starts at 0,0. Then, it will follow some trajectory
     by moving in steps. On each step, these changes occur in the following order:

     - The probe's x position increases by its x velocity.
     - The probe's y position increases by its y velocity.
     - Due to drag, the probe's x velocity changes by 1 toward the value 0; that is,
        it decreases by 1 if it is greater than 0,
        increases by 1 if it is less than 0,
        or does not change if it is already 0.
     - Due to gravity, the probe's y velocity decreases by 1.
     * @param args
     */

    // Return a pair of pairs. First is new position, second is new velocity
    static Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> transformXY(Pair<Integer, Integer> position, Pair<Integer, Integer> velocity) {
        int newX = position.fst + velocity.fst   // The probe's x position increases by its x velocity.
        int newY = position.snd + velocity.snd   // The probe's y position increases by its y velocity.
        int newVX = velocity.fst
        int newVY = velocity.snd
        if (newVX > 0) newVX--                   // x velocity decreases by 1 if it is greater than 0,
        else if (newVX < 0) newVX++              // x velocity increases by 1 if it is less than 0
        newVY--                                  // y velocity decreases by 1
        new Pair(new Pair<Integer, Integer>(newX, newY), new Pair<>(newVX, newVY))
    }

    @TailRecursive
    static boolean checkPosition(
            Pair<Integer, Integer> position,
            Pair<Integer, Integer> velocity,
            Pair<Integer, Integer> xTarget,
            Pair<Integer, Integer> yTarget) {
        // check if current position is in bounds, position.x is between x targets and position.y is between y targets
        if ((position.fst >= xTarget.fst && position.fst <= xTarget.snd) &&
            (position.snd >= yTarget.fst && position.snd <= yTarget.snd)) {
            return true
        }
        // If x position is past the max target x (pair.snd in this case), return false
        if (position.fst > xTarget.snd) {
            return false
        }
        // If y position is below the lowest target y (pair.fst in this case), return false
        if (position.snd < yTarget.fst) {
            return false
        }
        // keep trying
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> nextStep = transformXY(position, velocity)
        checkPosition(nextStep.fst, nextStep.snd, xTarget, yTarget)
    }

//  training input = "target area: x=20..30, y=-10..-5"
//  real input = "target area: x=248..285, y=-85..-56"
    static void main(String[] args) {
//        train()
        bothParts()
    }

    static void bothParts() {
        Pair<Integer, Integer> start = new Pair<>(0,0)
        Pair<Integer, Integer> xTarget = new Pair<>(248, 285)
        Pair<Integer, Integer> yTarget = new Pair<>(-85, -56)
        List<Pair<Integer, Integer>> matchingVelocities = new ArrayList<>()
        (-100..300).each {x ->          // Cannot start at 0, that goes nowhere; maxX seems like the furthest case to go out to
            (-100..300).each {y ->    // Looking for any slope now,
                Pair<Integer, Integer> velocity = new Pair<Integer, Integer>(x, y)
                if (checkPosition(start, velocity, xTarget, yTarget)) {
                    matchingVelocities.add(velocity)
                }
            }
        }
//        matchingVelocities
//                .sort{pair -> (pair.snd/pair.fst)}
//                .reverse()
//                .each {pair ->
//                    println(pair.toString() + "; slope: " + (pair.snd/pair.fst))
//                }
        println("Count of all potential velocities: " + matchingVelocities.size())

        // Part One
        // Pair[22,84]; slope: 3.8181818182 is the max for the training set. Find all positions for that one
//        incrementPosition(start, new Pair<Integer, Integer>(22, 84), xTarget, yTarget)
        // Position: Pair[253,3570]
        // Velocity: Pair[0,0]
        // Position: Pair[253,3570]
        // Velocity: Pair[0,-1]
    }

    static void train() {
        Pair<Integer, Integer> start = new Pair<>(0,0)
        Pair<Integer, Integer> xTarget = new Pair<>(20, 30)
        Pair<Integer, Integer> yTarget = new Pair<>(-10, -5)

        // Look for the highest slope of all velocity pairs that return true
        List<Pair<Integer, Integer>> matchingVelocities = new ArrayList<>()
        (0..40).each {x ->          // Cannot start at 0, that goes nowhere; maxX seems like the furthest case to go out to
            (-20..40).each {y ->    // maxY seems like a good start, but how high to go???
                Pair<Integer, Integer> velocity = new Pair<Integer, Integer>(x, y)
                if (checkPosition(start, velocity, xTarget, yTarget)) {
                    matchingVelocities.add(velocity)
                }
            }
        }
//        matchingVelocities
//                .sort{pair -> (pair.snd/pair.fst)}
//                .reverse()
//                .each {pair ->
//                    println(pair.toString() + "; slope: " + (pair.snd/pair.fst))
//                }
//        matchingVelocities.each {println(it)}
        println("Count of all potential velocities: " + matchingVelocities.size())
    }
}
