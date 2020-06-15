package com.example.digime.http;


import android.app.Application;
import android.content.Context;
import android.util.Log;


import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joo on 2017. 11. 23.
 */

public class httpconnect extends Application {
    private static final String TAG = httpconnect.class.getSimpleName();

    public static final String BASE_URL = "https://developers.nonghyup.com";   // tests

    private Context context;
    private Retrofit retrofit;
    private HttpService httpService;
    private static httpconnect httpsConnectionManager;

    public static httpconnect getInstance(Context context) {
        if (httpsConnectionManager == null) {
            httpsConnectionManager = new httpconnect(context);
        }
        return httpsConnectionManager;
    }

    private httpconnect(Context context) {
        this.context = context;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 해당 url
                .client(httpsClient())  // HTTPS, Header 설정
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpService = retrofit.create(HttpService.class);
    }


    public void login(RequestData RequestData, Context context) {
        Call<ResponseData> call = httpService.postLogin(RequestData);//companyID,
        Log.d(TAG, "login call = " + call.toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                Log.d(TAG, "api success = " + response.isSuccessful());
                Log.d(TAG, "login call = " + response.body().toJson());
                try {
                    RetriveandSaveJSONdatafromfile.objectToFile(response.body().toJson(), context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d(TAG, "sadf ");
            }
        });

    }

    private OkHttpClient httpsClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .addInterceptor(new AddHeaderInterceptor()) // header 세팅
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class AddHeaderInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        }
    }
}
