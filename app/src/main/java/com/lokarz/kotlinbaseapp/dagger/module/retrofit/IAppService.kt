package com.lokarz.kotlinbaseapp.dagger.module.retrofit

import com.lokarz.kotlinbaseapp.pojo.login.LoginPayload
import com.lokarz.kotlinbaseapp.pojo.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAppService {

    @POST("categories/read.php")
    fun login(@Body loginPayload: LoginPayload): Call<LoginResponse>

}