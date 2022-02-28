package com.example.randomwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Settings extends AppCompatActivity
{
    boolean is_lkz;
    boolean is_slang;
    boolean is_tzz;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        is_lkz = getIntent().getBooleanExtra("lkz", true);
        is_slang = getIntent().getBooleanExtra("slang", true);
        is_tzz = getIntent().getBooleanExtra("tzz", true);

        CheckBox cb_lkz = findViewById(R.id.cb_lkz);
        CheckBox cb_slang = findViewById(R.id.cb_slang);
        CheckBox cb_tzz = findViewById(R.id.cb_tzz);

        cb_lkz.setChecked(is_lkz);
        cb_slang.setChecked(is_slang);
        cb_tzz.setChecked(is_tzz);

        cb_lkz.setOnCheckedChangeListener((buttonView, isChecked) -> is_lkz = isChecked);
        cb_slang.setOnCheckedChangeListener((buttonView, isChecked) -> is_slang = isChecked);
        cb_tzz.setOnCheckedChangeListener((buttonView, isChecked) -> is_tzz = isChecked);

        Button b_back = findViewById(R.id.settings_button_back);
        b_back.setOnClickListener(v -> finish());

        Button b_save = findViewById(R.id.settings_button_save);
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.putExtra("lkz", is_lkz);
                i.putExtra("slang", is_slang);
                i.putExtra("tzz", is_tzz);
                setResult(21, i);
                finish();
            }
        });
    }


}