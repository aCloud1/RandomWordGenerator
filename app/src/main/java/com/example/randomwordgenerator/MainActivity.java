package com.example.randomwordgenerator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.BitSet;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    public final String shared_prefs = "sharedPreferences";
    boolean night_mode_on = false;
    BitSet enabled_dictionaries;

    TextView tw_word;
    TextView tw_info;
    Button button_next;
    Toolbar action_bar;
    Random rand;

    DatabaseHelper dbh;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw_word = findViewById(R.id.textview_word);
        tw_info = findViewById(R.id.tw_info);
        button_next = findViewById(R.id.button_next);
        action_bar = findViewById(R.id.toolbar);
        rand = new Random();
        enabled_dictionaries = new BitSet();
        dbh = new DatabaseHelper(this);

        action_bar.setTitle("");
        setSupportActionBar(action_bar);

        button_next.setOnClickListener(v ->
        {
            int random_table_id;
            do
            {
                random_table_id = rand.nextInt(3);
            } while (!enabled_dictionaries.get(random_table_id));

            int size = dbh.getTableSize(random_table_id);
            int random_word_id = rand.nextInt(size);

            random_word_id++;

            String random_word = dbh.getRandomWord(random_table_id, random_word_id);
            if(random_word.equals("-1"))
                return;

            String text = (random_table_id+1) + ": " + (random_word_id+1) + " / " + size;
            tw_info.setText(text);
            tw_word.setText(random_word);
        });


        SharedPreferences shared_preferences = getSharedPreferences(shared_prefs, MODE_PRIVATE);
        this.night_mode_on = shared_preferences.getBoolean("NightMode", false);
        if(this.night_mode_on)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_night)));
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_day)));
        }

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences shared_preferences = getSharedPreferences(shared_prefs, MODE_PRIVATE);
        this.night_mode_on = shared_preferences.getBoolean("NightMode", false);
        enabled_dictionaries.clear();
        if(shared_preferences.getBoolean("lkz", true))
            enabled_dictionaries.set(0);
        if(shared_preferences.getBoolean("slang", true))
            enabled_dictionaries.set(1);
        if(shared_preferences.getBoolean("tzz", true))
            enabled_dictionaries.set(2);

        boolean temp = shared_preferences.getBoolean("debugMode", false);
        if(temp)
            tw_info.setVisibility(View.VISIBLE);
        else
            tw_info.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.settings)
        {
            Intent i = new Intent(this, Settings.class);
            i.putExtra("sharedPreferences", shared_prefs);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
