package com.example.myapplication.eit_test

abstract class BaseCount {
    fun count(): Int {
        var sum = 0
        for (i in 0..6) {
            sum += getItem(i)
        }
        return sum
    }

    abstract fun getItem(index: Int): Int
}