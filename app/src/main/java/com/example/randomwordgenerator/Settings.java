package com.example.randomwordgenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Settings extends AppCompatActivity
{
    boolean is_lkz, is_slang, is_tzz, is_night_mode_on, is_debug_on;

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

        is_night_mode_on = shared_preferences.getBoolean("NightMode", false);
        CheckBox cb_night_mode = findViewById(R.id.cb_night_mode);
        cb_night_mode.setChecked(is_night_mode_on);
        cb_night_mode.setOnCheckedChangeListener((buttonView, isChecked) -> is_night_mode_on = isChecked);

        is_debug_on = shared_preferences.getBoolean("debugMode", false);
        CheckBox cb_debug = findViewById(R.id.cb_debug);
        cb_debug.setChecked(is_debug_on);
        cb_debug.setOnCheckedChangeListener((buttonView, isChecked) -> is_debug_on = isChecked);

        Button b_back = findViewById(R.id.settings_button_back);
        b_back.setOnClickListener(v -> finish());

        Button b_save = findViewById(R.id.settings_button_save);
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(is_night_mode_on)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                if(!(is_lkz || is_slang || is_tzz))
                    is_lkz = true;

                SharedPreferences.Editor editor = shared_preferences.edit();
                editor.putBoolean("lkz", is_lkz);
                editor.putBoolean("slang", is_slang);
                editor.putBoolean("tzz", is_tzz);
                editor.putBoolean("NightMode", is_night_mode_on);
                editor.putBoolean("debugMode", is_debug_on);
                editor.apply();
                finish();
            }
        });
    }


}