package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

import android.graphics.Picture;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
    }
    public void animation(){
        List<ImageView> images = new ArrayList<>();
        ImageView img = new ImageView(this);
        img.setImageIcon(Icon.createWithContentUri("ic_launcher.webp"));
        for(int i =0;i<30;i++){
            images.add(img);
        }
    }

}