import kotlin.random.Random

fun main() {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*"
    
    print("Enter password length: ")
    val length = readLine()!!.toInt()

    var password = ""

    for (i in 1..length) {
        val randomIndex = Random.nextInt(chars.length)
        password += chars[randomIndex]
    }

    println("Generated Password: $password")
}
