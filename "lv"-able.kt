
fun main() {
    val n = readLine()!!.toInt()
    val s = readLine()!!

    // Already contains "lv"
    if (s.contains("lv")) {
        println(0)
        return
    }

    // One replacement can make an adjacent pair "lv"
    for (i in 0 until n - 1) {
        if (s[i] == 'l' || s[i] == 'v' ||
            s[i + 1] == 'l' || s[i + 1] == 'v') {
            println(1)
            return
        }
    }

    // One reversal can make "lv" if l occurs before v
    val lIndex = s.indexOf('l')
    val vIndex = s.indexOf('v')

    if (lIndex != -1 && vIndex != -1 && lIndex < vIndex) {
        println(1)
        return
    }

    // Reverse adjacent "vl"
    if (s.contains("vl")) {
        println(1)
        return
    }

    println(2)
}
