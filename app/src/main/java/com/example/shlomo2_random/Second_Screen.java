package com.example.shlomo2_random;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Second_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        Intent intent = getIntent();
        int victory = intent.getIntExtra("victory", 0);
        int streak = intent.getIntExtra("streak", 0);
        TextView wins = findViewById(R.id.wins);
        wins.setText("All time wins: " + victory + "\nCurrent Streak: " + streak);
    }

    public void move_screen(View view) {
         if (view.getId() == R.id.imageButton2) {
            Intent intent2 = new Intent();
            setResult   (RESULT_OK, intent2);
            finish();
        }
    }

    public void goToSite (View view) {
        goToUrl ("https://nevokaplan4.wixsite.com/bibis-adventure");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}