package com.example.koi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HomePage extends AppCompatActivity {

    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int images[] = {R.drawable.menu, R.drawable.signature_drinks, R.drawable.milk_tea, R.drawable.taro_milktea,R.drawable.brownsugar_milktea};

        v_flipper = findViewById(R.id.v_flipper);

        for  (int image: images)
        {
            flipperImages(image);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu:
                Toast.makeText(this,"Item 1 selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Item 2 selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this,"Item 3 selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this,"Subitem 1 selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this,"Subitem 2 selected",Toast.LENGTH_SHORT).show();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    public void flipperImages(int image)
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(2000);
        v_flipper.setAutoStart(true);

        //animation
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setInAnimation(this, android.R.anim.slide_out_right);

    }

    Intent intent = getIntent();

    public void milktea(View view)
    {
        Intent intent = new Intent(this,MilkTeaPage.class);
        startActivity(intent);
    }

    public void flavouredtea(View view)
    {
        Intent intent = new Intent(this,FlavouredTeaPage.class);
        startActivity(intent);
    }

    public void macchiato(View view)
    {
        Intent intent = new Intent(this,SignatureMacchiatoPage.class);
        startActivity(intent);
    }



}
