package com.example.myapplication

class Student : Person, Study {
    var age: Int = 0

    constructor(age: Int)

    fun getAge() {
        println("age: $age")
    }

    override fun study() {
        println(1)
    }

    override fun eat() {
        println(2)
    }
}