import kotlin.random.Random

fun main() {
    val randomNumber = Random.nextInt(1, 101) 
    var attempts = 0
    var guess: Int

    println(" Guess the number between 1 and 100")

    do {
        print("Enter your guess: ")
        guess = readLine()!!.toInt()
        attempts++

        when {
            guess > randomNumber -> println("Too high! Try again.")
            guess < randomNumber -> println("Too low! Try again.")
            else -> println(" Correct! You guessed it in $attempts attempts.")
        }

    } while (guess != randomNumber)
}
