package com.example.myapplication

open class Person {
    var name: String = ""

    fun getName(study: Study?) {
        study?.study()
        println(name)
    }
}