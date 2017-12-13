package me.jim.wx.awesomebasicpractice.rxjava.abc;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by wx on 2017/12/1.
 */
public interface Api {
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    class LoginResponse {
    }

    class LoginRequest {
    }

    class RegisterResponse {
    }

    class RegisterRequest {
    }
}
