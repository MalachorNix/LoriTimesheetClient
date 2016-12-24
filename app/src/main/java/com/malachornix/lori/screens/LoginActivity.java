package com.malachornix.lori.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.malachornix.lori.R;
import com.malachornix.lori.api.LoriApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoriApi loriApi;
    private static final String LOGIN_TAG = "LOGIN";
    private SharedPreferences preferences;
    private EditText loginText;
    private EditText passwordText;
    private CheckBox rememberMeCheckBox;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginText = (EditText) findViewById(R.id.input_login);
        passwordText = (EditText) findViewById(R.id.input_password);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.checkBox);

        preferences = getSharedPreferences("LoriApp", MODE_PRIVATE);

        loginButton = (Button) findViewById(R.id.btn_login);

        boolean rememberMe = preferences.getBoolean("RememberMe", false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        if (rememberMe) {
            String username = preferences.getString("Username", "");
            String password = preferences.getString("Password", "");
            loginText.setText(username);
            passwordText.setText(password);
            login();
        }
    }

    private void login() {
        String baseUrl =
                preferences.getString("BaseUrl", getResources().getString(R.string.apiUrl));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loriApi = retrofit.create(LoriApi.class);

        final String username = loginText.getText().toString();
        final String password = passwordText.getText().toString();
        Call<String> loginCall = loriApi.login(username, password);

        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();
                String sessionId = response.body();

                if (isOkStatus(statusCode) && sessionId != null) {

                    Log.d(LOGIN_TAG, "Session id is: " + sessionId);

                    preferences.edit().putString("SessionId", sessionId).apply();
                    preferences.edit().putString("Username", username).apply();
                    preferences.edit().putString("Password", password).apply();
                    preferences.edit().putBoolean("RememberMe", rememberMeCheckBox.isChecked()).apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(LoginActivity.this, "Incorrect login or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(LOGIN_TAG, t.toString());
                Toast toast = Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private boolean isOkStatus(int responseCode) {
        return responseCode == 200;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
