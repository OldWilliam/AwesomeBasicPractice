package me.jim.wx.awesomebasicpractice.other.di

import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

class DiApplication {

    init {
        startKoin {
            printLogger()
            modules(listOf(helloModule))
        }
    }

    // Inject HelloService
    private val helloService by inject(HelloService::class.java)

    // display our data
    fun sayHello() = helloService.sayHello()
}