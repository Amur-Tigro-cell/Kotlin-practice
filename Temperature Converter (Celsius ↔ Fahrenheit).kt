fun main() {
    println("Temperature Converter")
    println("1. Celsius to Fahrenheit")
    println("2. Fahrenheit to Celsius")
    print("Choose option: ")

    val choice = readLine()!!.toInt()

    when (choice) {
        1 -> {
            print("Enter Celsius: ")
            val c = readLine()!!.toDouble()
            val f = (c * 9/5) + 32
            println("Fahrenheit: $f")
        }
        2 -> {
            print("Enter Fahrenheit: ")
            val f = readLine()!!.toDouble()
            val c = (f - 32) * 5/9
            println("Celsius: $c")
        }
        else -> println("Invalid choice!")
    }
}
