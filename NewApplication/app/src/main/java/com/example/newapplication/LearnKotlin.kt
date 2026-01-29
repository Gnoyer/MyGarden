package com.example.newapplication

fun main() {
    val a = 1
    val b = 2
    val c = a + b
    val d = c
    var sm = 0
    sm++
    println(d)
    println("Hello World")
}

fun addSome (a: Int, b: Int) = a+b
fun addSome1 (a: Int, b: Int) = if (a>b) a else b