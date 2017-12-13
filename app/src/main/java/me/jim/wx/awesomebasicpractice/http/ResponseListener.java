package me.jim.wx.awesomebasicpractice.http;

/**
 * Created by wx on 2017/12/13.
 */

public interface ResponseListener<E> {
    void onResult(E e);
}
