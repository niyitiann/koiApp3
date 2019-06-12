package com.example.koi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static int timeout = 3000;
    TextView txt;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.txt);
        img=findViewById(R.id.img);

        Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.myanim);
        img.startAnimation(animation);
        txt.startAnimation(animation);


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent intent=new Intent(MainActivity.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        },timeout);
    }
}
