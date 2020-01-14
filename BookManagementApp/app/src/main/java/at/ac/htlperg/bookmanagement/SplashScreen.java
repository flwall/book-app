package at.ac.htlperg.bookmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import java.lang.Runnable;

public class SplashScreen extends AppCompatActivity {
    private int SPLASH_TIME = 2000;
    private Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myHandler = new Handler();
        myHandler.postDelayed(new Runnable(){
            public void run(){
                goToMainActivity();
            }
        }, SPLASH_TIME);

    }

    private void goToMainActivity(){
        Intent mainAcivityIntent = new Intent();
        startActivity(mainAcivityIntent);
        finish();
    }
}

