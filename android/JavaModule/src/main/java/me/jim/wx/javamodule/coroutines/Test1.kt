package me.jim.wx.javamodule.coroutines

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

fun main() {
    val ctx = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    runBlocking(ctx) {
        repeat(10) {
            launch {
                while (true) {
//                    delay(1000)
                    println("task $it runs on thread ${Thread.currentThread().id}")
                }
            }
        }
    }
}