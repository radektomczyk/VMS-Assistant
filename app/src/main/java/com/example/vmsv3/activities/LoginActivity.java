package com.example.vmsv3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vmsv3.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void loginClick(View view) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.equals("admin") && password.equals("admin")) {
            // Jeśli login i hasło są poprawne, przekieruj użytkownika do ekranu głównego.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // W przeciwnym razie wyświetl komunikat o błędzie.
            Toast.makeText(this, "Niepoprawny login lub hasło", Toast.LENGTH_SHORT).show();
        }
    }
}

