package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
ConstraintLayout gameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLayout=findViewById(R.id.game_layout);
        hideView(gameLayout);

    }

    public void switch_player(View view){
        ToggleButton switch_player = (ToggleButton) view;
        if(switch_player.isChecked()){
            switch_player.setText("O");
        }
        else{
            switch_player.setText("X");
        }
        ((Button)findViewById(R.id.new_game)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_player.setEnabled(true);
                getButton_list().stream().forEach(button -> button.setEnabled(true));
                getButton_list().stream().forEach(button -> button.setText("_"));
            }
        });
    }
    int turn = 0;

    public void start_game(View view){
        showView(gameLayout);
    }
    public void showView(View view){
        view.setVisibility(View.VISIBLE);
    }
    public void hideView(View view){
        view.setVisibility(View.INVISIBLE);
    }


    public void game(View view){
        ToggleButton switch_player = findViewById(R.id.switch_player);
        String player1_id = (String) switch_player.getText();
        String player2_id ="" ;
        switch_player.setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.set_player)).setVisibility(View.INVISIBLE);

        Button clicked_button = (Button)view;
        if(Objects.equals(player1_id, "X")){
            player2_id="O";
        }else{
            player2_id="X";
        }

        if(turn%2==0){
            clicked_button.setText(player1_id);
            Toast.makeText(this,"Player2 turn "+ player2_id,Toast.LENGTH_SHORT).show();

        }else{
            clicked_button.setText(player2_id);
            Toast.makeText(this,"Player1 turn "+player1_id,Toast.LENGTH_LONG).show();

        }
        clicked_button.setEnabled(false);
        add_button(clicked_button);
        turn++;


    }

    List<Button> button_list = new ArrayList<>();

    public void add_button(Button button){
        button_list.add(button);
    }
    public List<Button> getButton_list(){
        return button_list;
    }

}