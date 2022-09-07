package me.jim.wx.awesomebasicpractice.other.di

import android.widget.Toast
import me.jim.wx.awesomebasicpractice.util.ContextHelper

class HelloService: IHelloService {
    override fun sayHello() {
        Toast.makeText(ContextHelper.getContext(), "di Hello", Toast.LENGTH_SHORT).show()
    }
}