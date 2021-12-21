package day_21

import groovy.transform.TailRecursive

class DiracDice {

    static void main(String[] args) {
        Game game = new Game(6, 8) // Part 1
//        Game game = new Game(4, 8) // Training Data
        while(game.incomplete()) {
            game = incrementGame(game)
        }
        println(game)
    }

    @TailRecursive
    static Game incrementGameStep(Game game, int step) {
        println("Game State for step " + step + ";\n" + game)
        if (step == 0) {
            return game
        }
        incrementGameStep(incrementGame(game), step - 1)
    }

    static Game incrementGame(Game game) {
        game.rollDice()
        int p1Roll = game.die
        game.rollDice()
        p1Roll += game.die
        game.rollDice()
        p1Roll += game.die

        game.p1Position += p1Roll
        while(game.p1Position > 10) {
            game.p1Position = game.p1Position - 10
        }
        game.p1Score += game.p1Position

        if (game.incomplete()) {
            game.rollDice()
            int p2Roll = game.die
            game.rollDice()
            p2Roll += game.die
            game.rollDice()
            p2Roll += game.die

            game.p2Position += p2Roll
            while(game.p2Position > 10) {
                game.p2Position = game.p2Position - 10
            }
            game.p2Score += game.p2Position
        }
        game
    }

    static class Game {
        int p1Position
        int p1Score
        int p2Position
        int p2Score
        int die
        int rollCount
        boolean incomplete() {
            p1Score < 1000 && p2Score < 1000
        }
        void rollDice() {
            die++
            rollCount++
            if (die > 100) {
                die = 1
            }
        }
        Game(int p1Position, int p2Position) {
            this.p1Position = p1Position
            this.p1Score = 0
            this.p2Position = p2Position
            this.p2Score = 0
            this.die = 0
            this.rollCount = 0
        }
        String toString() {
            "Game State: \n" +
                    "Player 1 Position: " + p1Position + "; Score: " + p1Score + "\n" +
                    "Player 2 Position: " + p2Position + "; Score: " + p2Score + "\n" +
                    "Die State: " + die + "; Roll Count: " + rollCount + "\n"
        }
    }

}
