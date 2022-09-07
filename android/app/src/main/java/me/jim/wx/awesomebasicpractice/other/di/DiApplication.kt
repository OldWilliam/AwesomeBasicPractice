package me.jim.wx.awesomebasicpractice.other.di

import org.koin.java.KoinJavaComponent.inject

class DiApplication {

    // Inject HelloService
    private val helloService by inject(IHelloService::class.java)

    // display our data
    fun sayHello() = helloService.sayHello()
}