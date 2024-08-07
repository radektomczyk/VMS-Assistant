package com.example.vmsv3.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiClient;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.api.LoginResponse;
import com.example.vmsv3.transport.LoginDto;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        apiService = ApiClient.getApiClient().create(ApiService.class);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    }

    public void loginClick(View view) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        LoginDto loginDto = new LoginDto(username, password);

        Call<LoginResponse> call = apiService.login(loginDto);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Logging in successful", Toast.LENGTH_SHORT).show();
                    String accessToken = null;
                    try {
                        assert response.body() != null;
                        accessToken = response.body().getAccessToken();
                        Log.d("Login", "onResponse: Token" + accessToken);
                        saveToken(accessToken);


                    }catch (NullPointerException e){
                        Log.e("AccessToken", "NullPointerException occurred: " + e.getMessage());
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("ACCESS_TOKEN", accessToken);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Couldn't log in", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Logging error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Login", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
    private void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ACCESS_TOKEN", token);
        editor.apply();
    }
}
