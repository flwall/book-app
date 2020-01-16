package activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;

import java.lang.Runnable;

import at.ac.htlperg.bookmanagement.R;

public class SplashScreen extends AppCompatActivity {
    private int SPLASH_TIME = 2000;
    private Handler myHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        Intent mainActivityIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }

}

