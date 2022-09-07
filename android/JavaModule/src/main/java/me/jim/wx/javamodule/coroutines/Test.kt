package me.jim.wx.javamodule.coroutines

import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Executors

fun main() {

    val context = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    val list = LinkedList<String>()

    GlobalScope.launch(
        context
    ) {
        while (true) {
            if (list.size >= 5) {
                val name = Thread.currentThread().name
                println("t:$name consume " + list.pop())
            } else {
                delay(10)
            }
        }
    }

    GlobalScope.launch(context) {
        for (i in 0..20000) {
            val toString = i.toString()
            list.addLast(toString)
            val name = Thread.currentThread().name
            println("t:$name produce " + toString)

            if (i % 5 == 0) {
                delay(10)
            }
        }
    }
}