package me.jim.wx.awesomebasicpractice.other.di

import org.koin.dsl.module

val helloModule = module {
    single { HelloService() as IHelloService}
}