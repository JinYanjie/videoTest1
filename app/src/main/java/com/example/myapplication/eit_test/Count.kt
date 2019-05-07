package com.example.myapplication.eit_test

class Count : BaseCount() {
    private val list = listOf<Int>(1, 2, 3, 4, 5)
    override fun getItem(index: Int): Int {
        return if (index < list.size) list[index] else 0
    }
}