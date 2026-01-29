package com.example.myapplication

fun main() {
    val a = 1..10
    for (i in a) {
        print(i)
    }
    val b = 1 until 10
    for (i in b step 2) {
        print(i)
    }
    val c = 10 downTo 1
    for (i in c) {
        print(i)
    }
    print(getScore(getNumber("0")))
}

//fun getNumber(number: String): Int {
//    return number.toInt()
//}
//fun getNumber(number: String) = number.toInt()
fun getNumber(number: String) = if (number.isEmpty()) 0 else number.toInt()

fun getScore(score: Int) = when (score) {
    in 0..60 -> "1"
    in 61..70 -> "2"
    in 71..80 -> "良好"
    in 81..90 -> "优秀"
    else -> "优秀"
}
