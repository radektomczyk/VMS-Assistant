package com.example.vmsv3.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // private static final String BASE_URL = "https://samochodio.onrender.com/api/";
    private static final String BASE_URL = "http://192.168.1.7:8080/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + accessToken);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }

    // get current ip address to develop app locally
    private static String getCurrentIpAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.d("CurrentIP", "getCurrentIpAddress: " + e);
            return "";
        }
    }

    // do not validate certificates xd, trust anyone lmao
    private static void handleSSLCertificate() {
        try {
            // create trust manager
            X509TrustManager[] trustAllCerts = new X509TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // install trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // set up trust manager
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> {
                // allow all hostnames
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
