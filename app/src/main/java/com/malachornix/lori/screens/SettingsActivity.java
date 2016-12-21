package com.malachornix.lori.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.malachornix.lori.R;


public class SettingsActivity extends AppCompatActivity {

    private static final String PREFERENCES_IP_ADDRESS = "IpAddress";
    private static final String PORT_NUMBER = "PortNumber";
    private static final String PREFERENCES_BASE_URL = "BaseUrl";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final EditText ipAddressText = (EditText) findViewById(R.id.settings_ip_address);
        final EditText portNumberText = (EditText) findViewById(R.id.settings_port_number);
        Button applyButton = (Button) findViewById(R.id.settings_btn_apply);

        final SharedPreferences preferences = getSharedPreferences("LoriApp", MODE_PRIVATE);
        ipAddressText.setText(preferences.getString(PREFERENCES_IP_ADDRESS, "46.0.194.16"));
        portNumberText.setText(String.valueOf(preferences.getInt(PORT_NUMBER, 8080)));

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddress = ipAddressText.getText().toString();
                String strPortNumber = portNumberText.getText().toString();
                int portNumber = Integer.parseInt(strPortNumber);
                String baseUrl =
                        "http://" + ipAddress + ":" + portNumber + "/app/dispatch/api/";
                preferences.edit()
                        .putString(PREFERENCES_IP_ADDRESS, ipAddress)
                        .putInt(PORT_NUMBER, portNumber)
                        .putString(PREFERENCES_BASE_URL, baseUrl)
                        .apply();

                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
