package com.minew.navigation.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {

    GlobalScope.launch {
        delay(1000L)
        println("world")
    }
    print("Hello,")
    Thread.sleep(1100L)

}