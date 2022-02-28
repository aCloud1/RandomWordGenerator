package com.example.randomwordgenerator;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

public class Dictionary
{
    boolean enabled = true;
    Vector<String> words;

    public Dictionary(Context context, String file_name)
    {
        InputStream in;
        Scanner scanner;
        words = new Vector<>();
        try
        {
            in = context.getAssets().open(file_name);
            scanner = new Scanner(in);
            while(scanner.hasNextLine())
                words.add(scanner.nextLine());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getWord(int id) { return words.get(id); }
    public void setEnabled(boolean value) { this.enabled = value; }
    public int getSize() { return this.words.size(); }

}
