package com.minew.navigation.test

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.system.measureTimeMillis

fun main() {
//    test2()
//    test3()

//    test4()

//    test5()
//    test6()

//    test7()

    test8()
}

private fun test8() = runBlocking {

    launch {
        repeat(5) {
            println("test8 launch $it")
            delay(100)
        }
    }

    calculate8().collect { println(it) }
}

private fun calculate8(): Flow<Int> = flow {
    repeat(5) {
//        delay(100)
        Thread.sleep(100)
        emit(it)
    }
}

private fun test7() {
    /*val sequence = foo()
    sequence.forEach { values ->
        println(values)
    }*/

    runBlocking {
        foo2().forEach {
            println(it)
        }
    }
}

private suspend fun foo2(): List<Int> {
    delay(1000L)
    return listOf(1, 2, 3, 4, 5)
}

private fun foo(): Sequence<Int> = sequence {
    repeat(5) {
        Thread.sleep(100L)
        yield(it)
    }
}

private fun test6() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch (e: Exception) {
        println("custom exception")
    }
}

private suspend fun failedConcurrentSum() = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE)
            42
        } finally {
            println("finally,it will be cancelled")
        }
    }

    val two = async<Int> {
        println("throw Exception!")
        throw ArithmeticException()
    }

    one.await() + two.await()
}

private fun test5() {
    val job = GlobalScope.launch {
        val time = measureTimeMillis {
            println("the answer is ${concurrentSum()}")
        }
        println("use times is $time")
    }

    runBlocking {
        job.join()
    }

}

private suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

private fun test4() {
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

private fun test3() {
    val job = GlobalScope.launch {
        val time = measureTimeMillis {
            val oneJob = GlobalScope.async {
                calculateOne()
            }

            val twoJob = GlobalScope.async {
                calculateTwo()
            }
            println("calculate result is ${oneJob.await() + twoJob.await()}")
        }
        println("waste time is $time")
    }

    runBlocking {
        job.join()
    }
}

private fun test2() {
    val startTime = System.currentTimeMillis()
    println(startTime)
    val job = GlobalScope.launch {
        println(calculateOne() + calculateTwo())
    }
    println("11")
    runBlocking {
        println("22")
        job.join()
        val endTime = System.currentTimeMillis()
        println(endTime)
        println((endTime - startTime))
    }
    println("33")
}

private suspend fun calculateOne(): Int {
    delay(1000L)
    return 11
}

private suspend fun calculateTwo(): Int {
    delay(1000L)
    return 19
}

private fun test() {
    val ddd = getLength {
        plus(88).length
    }
    println(ddd)

    println(getLength(12) {
        "AK47".length
    })

    with(User("Jokeen"), {
        println(name)
    })
}

private fun getLength(block: String.() -> Int): Int {
    val ii = "Jokeen1898".block()
    return ii
}

private fun getLength(m: Int, block: (String) -> Int): Int {
    return block("res")
}

private data class User(val name: String)