import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    println("Enter first number:")
    val num1 = scanner.nextDouble()

    println("Enter operator (+, -, *, /):")
    val operator = scanner.next()[0]

    println("Enter second number:")
    val num2 = scanner.nextDouble()

    val result: Double

    when (operator) {
        '+' -> result = num1 + num2
        '-' -> result = num1 - num2
        '*' -> result = num1 * num2
        '/' -> {
            if (num2 != 0.0) {
                result = num1 / num2
            } else {
                println("Error! Division by zero.")
                return
            }
        }
        else -> {
            println("Invalid operator!")
            return
        }
    }

    println("Result: $result")
}
