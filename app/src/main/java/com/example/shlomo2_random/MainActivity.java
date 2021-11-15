package com.example.shlomo2_random;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static Random rnd = new Random();
    int maxRand;
    int main_num;
    int rand;
    TextView main;
    Toast toast;
    int checked_num;
    Button small, middle, big;
    TextView hint;
    boolean first;
    int victory = 0;
    int current_streak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maxRand = 100;
        main_num = 0;
        rand = rnd.nextInt(100) + 1;
        main = findViewById(R.id.main_num);
        main.setText(main_num + "");
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        hint = findViewById(R.id.hint);
        first = true;
        small = findViewById(R.id.small);
        middle = findViewById(R.id.middle);
        big = findViewById(R.id.big);
        current_streak = 0;

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

        int saved = sh.getInt("victory", 0);

        victory = saved;
    }

    public void move_screen(View view) {
        if (view.getId() == R.id.imageButton) {
            Intent intent = new Intent(this, Second_Screen.class);
            intent.putExtra("victory", victory);
            intent.putExtra("streak", current_streak);
            startActivityForResult(intent, 10);
        }
    }

    public void change(View view)
    {
        if (view.getId() == R.id.button7) {
            main_num++;
        }
        else if (view.getId() == R.id.button8){
            main_num += 10;
        }
        else if (view.getId() == R.id.button5){
            main_num--;
        }
        else if (view.getId() == R.id.button6){
            main_num -= 10;
        }
        if (main_num < 0)
            main_num = maxRand;
        else if (main_num > maxRand)
            main_num = 0;
        main.setText(main_num + "");
    }

    public void choose(View view) {
        toast.cancel();

        switch(view.getId()) {
            case R.id.small:
                rand = rnd.nextInt(100) + 1;
                maxRand = 100;
                toast.setText("Random 1 - 100");
                small.setTextColor(Color.CYAN);
                middle.setTextColor(Color.BLACK);
                big.setTextColor(Color.BLACK);
                break;

            case R.id.middle:
                rand = rnd.nextInt(444) + 1;
                maxRand = 444;
                toast.setText("Random 1 - 444");
                small.setTextColor(Color.BLACK);
                middle.setTextColor(Color.CYAN);
                big.setTextColor(Color.BLACK);
                break;

            case R.id.big:
                rand = rnd.nextInt(1000) + 1;
                maxRand = 1000;
                toast.setText("Random 1 - 1000");
                small.setTextColor(Color.BLACK);
                middle.setTextColor(Color.BLACK);
                big.setTextColor(Color.CYAN);
                break;
        }
        first = true;
        checked_num = -1;
        hint.setText("");
        toast.show();
    }


    public void check(View view)
    {
        System.out.println(rand + "\nChecked: " + checked_num + "\nMain: " + main_num);
        if (rand == main_num)
        {
            hint.setText("Perfect!");
            hint.setTextColor(Color.GREEN);
            checked_num = main_num;
            first = true;
            current_streak++;
            victory++;
        }

        else if (first) {
            if (rand > main_num) {
                hint.setText(main_num + " is too low!");
            } else {
                hint.setText(main_num + " is too high!");
            }
            hint.setTextColor(Color.RED);
            first = false;
            checked_num = main_num;
        }

        else if (!first)
        {
            if (main_num == checked_num)
            {
                hint.setText("Do something...");
                hint.setTextColor(Color.BLACK);
            }

            else
            {

                if (Math.abs(main_num - rand) <= 10)
                {
                    hint.setText("VERY CLOSE");
                    hint.setTextColor(Color.MAGENTA);
                }

                else if (((main_num > checked_num) && (main_num < rand)) || ((main_num < checked_num) && (main_num > rand)))
                {
                    System.out.println( "left: " + (main_num > checked_num && main_num < rand));
                    System.out.println( "right: " + ((main_num < checked_num) && (main_num > rand)));
                    hint.setText("Getting closer");
                    hint.setTextColor(Color.rgb(255, 140, 0));

                }
                else if (((main_num < checked_num) && (main_num < rand)) || (main_num > checked_num && main_num > rand))
                {
                    hint.setText("Getting further");
                    hint.setTextColor(Color.CYAN);
                }
                checked_num = main_num;
            }
        }
    }


    @Override
    protected void onStop () {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("victory", victory);

        myEdit.commit();
        super.onStop();
        toast.cancel();

    }

}