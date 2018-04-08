package com.example.akgarhwal.puzzlex;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static int LEVEL=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LEVEL = 3;
    }
    public void puzzle8(View view){
        this.LEVEL = 3;
        Intent in = new Intent(this,PuzzleActivity.class);
        startActivity(in);
        finish();
    }
    public void puzzle15(View view){
        this.LEVEL = 4;
        Intent in = new Intent(this,PuzzleActivity.class);
        startActivity(in);
        finish();
    }
    public void puzzle24(View view){
        this.LEVEL = 5;
        Intent in = new Intent(this,PuzzleActivity.class);
        startActivity(in);
        finish();
    }
    public void quit(View view){
        System.exit(0);
    }
}
