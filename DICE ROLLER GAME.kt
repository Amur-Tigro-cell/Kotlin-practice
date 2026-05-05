import kotlin.random.Random

fun main() {

    var playerScore = 0
    var computerScore = 0

    println("Welcome to Dice Battle Game!")
    println("You vs Computer - 5 Rounds\n")

    for (round in 1..5) {
        println("Round $round")

        val playerRoll = Random.nextInt(1, 7)
        val computerRoll = Random.nextInt(1, 7)

        println("You rolled: $playerRoll")
        println("Computer rolled: $computerRoll")

        when {
            playerRoll > computerRoll -> {
                println(" You win this round!\n")
                playerScore++
            }
            playerRoll < computerRoll -> {
                println("Computer wins this round!\n")
                computerScore++
            }
            else -> {
                println(" It's a draw!\n")
            }
        }
    }

    println("FINAL SCORE")
    println("You: $playerScore")
    println("Computer: $computerScore")

    when {
        playerScore > computerScore -> println(" YOU WIN THE GAME!")
        playerScore < computerScore -> println(" COMPUTER WINS THE GAME!")
        else -> println("GAME DRAW!")
    }
}
