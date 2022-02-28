package com.example.randomwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Settings extends AppCompatActivity
{
    boolean is_lkz, is_slang, is_tzz;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences shared_preferences = getSharedPreferences(getIntent().getStringExtra("sharedPreferences"), MODE_PRIVATE);

        is_lkz = shared_preferences.getBoolean("lkz", true);
        CheckBox cb_lkz = findViewById(R.id.cb_lkz);
        cb_lkz.setChecked(is_lkz);
        cb_lkz.setOnCheckedChangeListener((buttonView, isChecked) -> is_lkz = isChecked);

        is_slang = shared_preferences.getBoolean("slang", true);
        CheckBox cb_slang = findViewById(R.id.cb_slang);
        cb_slang.setChecked(is_slang);
        cb_slang.setOnCheckedChangeListener((buttonView, isChecked) -> is_slang = isChecked);

        is_tzz = shared_preferences.getBoolean("tzz", true);
        CheckBox cb_tzz = findViewById(R.id.cb_tzz);
        cb_tzz.setChecked(is_tzz);
        cb_tzz.setOnCheckedChangeListener((buttonView, isChecked) -> is_tzz = isChecked);

        Button b_back = findViewById(R.id.settings_button_back);
        b_back.setOnClickListener(v -> finish());

        Button b_save = findViewById(R.id.settings_button_save);
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = shared_preferences.edit();
                editor.putBoolean("lkz", is_lkz);
                editor.putBoolean("slang", is_slang);
                editor.putBoolean("tzz", is_tzz);
                editor.apply();
                finish();
            }
        });
    }


}