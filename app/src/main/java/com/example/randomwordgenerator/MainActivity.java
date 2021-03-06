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
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;
import java.util.Vector;


public class MainActivity extends AppCompatActivity
{
    public final String shared_prefs = "sharedPreferences";
    Vector<Dictionary> dictionaries;
    boolean night_mode_on = false;

    TextView tw_word;
    TextView tw_info;
    Button button_next;
    Toolbar action_bar;
    Random rand;

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

        action_bar.setTitle("");
        setSupportActionBar(action_bar);

        dictionaries = new Vector<Dictionary>();
        dictionaries.add(new Dictionary(this,"lkz.dat"));
        dictionaries.add(new Dictionary(this,"slang.dat"));
        dictionaries.add(new Dictionary(this,"tzz.dat"));

        button_next.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v)
            {
                int random_dict_id;
                do
                {
                    random_dict_id = rand.nextInt(dictionaries.size());
                } while (!dictionaries.get(random_dict_id).enabled);

                int random_word_id = rand.nextInt(dictionaries.get(random_dict_id).getSize());

                String random_word = dictionaries.get(random_dict_id).getWord(random_word_id);

                String text = random_dict_id+1 + ": " + random_word_id + " / " + dictionaries.get(random_dict_id).getSize();
                tw_info.setText(text);
                tw_word.setText(random_word);
            }
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
        dictionaries.get(0).setEnabled(shared_preferences.getBoolean("lkz", true));
        dictionaries.get(1).setEnabled(shared_preferences.getBoolean("slang", true));
        dictionaries.get(2).setEnabled(shared_preferences.getBoolean("tzz", true));
        boolean temp = shared_preferences.getBoolean("debugMode", false);
        if(temp)
            tw_info.setVisibility(View.VISIBLE);
        else
            tw_info.setVisibility(View.INVISIBLE);
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
        if(item.getItemId() == R.id.about)
            Toast.makeText(this, "ABOUT", Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }
}
