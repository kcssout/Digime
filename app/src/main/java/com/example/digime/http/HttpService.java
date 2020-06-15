package com.example.digime.http;


import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Joo on 2017. 11. 24.
 */

public interface HttpService {

    // POST 방식이지만 GET 방식처럼 URL에 파라미터 넣어 넘기듯이 씀
    @Headers("Content-Type: application/json")
    @POST("/InquireDepositorAccountNumber.nh")
    Call<ResponseData> postLogin(
                                @Body RequestData body);


}
