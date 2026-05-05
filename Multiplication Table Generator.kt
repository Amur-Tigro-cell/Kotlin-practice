fun main() {
    print("Enter a number: ")
    val number = readLine()!!.toInt()

    println("Multiplication Table of $number:")
    for (i in 1..10) {
        println("$number x $i = ${number * i}")
    }
}
