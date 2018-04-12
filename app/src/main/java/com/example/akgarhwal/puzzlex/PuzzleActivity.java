package com.example.akgarhwal.puzzlex;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PuzzleActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap = null;
    private PuzzleBoardView boardView;
    private static TextView bestScore;

    private ImageButton solveButton;
    private static TextView score;
    private static SharedPreferences sharedpreferences;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        score = (TextView) findViewById(R.id.score2);
        bestScore = (TextView) findViewById(R.id.bestscore2);

        // This code programmatically adds the PuzzleBoardView to the UI.
        RelativeLayout container = (RelativeLayout) findViewById(R.id.puzzle_container);
        boardView = new PuzzleBoardView(this);
        // Some setup of the view.
        boardView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        container.addView(boardView);

        solveButton = (ImageButton) findViewById(R.id.solve_button);
        sharedpreferences = getSharedPreferences("BestScore", Context.MODE_PRIVATE);
        setBestScore(-1);

        container.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_image);
                boardView.initialize(bitmap);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_puzzle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, 777);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == 1 && resultCode == RESULT_OK ){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            boardView.initialize(bitmap);
        }
        else{
            Toast.makeText(getApplicationContext(),"Image not selected",Toast.LENGTH_SHORT).show();
        }


    }

    public void shuffleImage(View view) {
        boardView.shuffle();
    }

    public void solve(View view) {

        solveButton.setClickable(false);
        boardView.solve();
        solveButton.setClickable(true);

    }

    public void home(View view){
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
        finish();
    }

    public static void setScore(int Score){
        score.setText(""+Score);
        return ;
    }

    public static void setBestScore(int bScore){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        String key = Integer.toString(MainActivity.LEVEL);

        int bestscore = sharedpreferences.getInt(key,-1);
        if(bestscore == -1 ){
            bestScore.setText("--");
        }
        else {
            bestScore.setText("" + bestscore);
        }
        if(bScore == -1){
            return ;
        }

        String tempScore=  bestScore.getText().toString();
        if(tempScore.equals("--")){
            bestScore.setText("" + bScore);
            editor.putInt(key, bScore);
            editor.commit();
            return ;
        }
        int temp = Integer.parseInt(tempScore);
        if(temp > bScore) {
            bestScore.setText("" + bScore);
            editor.putInt(key, bScore);
            editor.commit();
        }
    }
}
