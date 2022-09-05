package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        ToggleButton switchPlayer = findViewById(R.id.switch_player);
        if(switchPlayer.getVisibility()==View.INVISIBLE){
            switchPlayer.setVisibility(View.VISIBLE);
        }
        String player1_id = (String) switchPlayer.getText();
        String player2_id ="" ;
        switchPlayer.setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.set_player)).setVisibility(View.INVISIBLE);

        Button clickedButton = (Button) view;
        if(Objects.equals(player1_id, "X")){
            player2_id="O";
        }else{
            player2_id="X";
        }
        TextView info = ((TextView)findViewById(R.id.info));
        if(turn%2==0){
            clickedButton.setText(player1_id);
            info.setText("Player2 turn "+ player2_id);

        }else{
            clickedButton.setText(player2_id);
            info.setText("Player1 turn "+player1_id);

        }
        clickedButton.setEnabled(false);
        addButton(clickedButton);
        turn++;

        //the winning part





    }
    public void newGame(View view){
        TextView info = ((TextView)findViewById(R.id.info));
            showView(findViewById(R.id.set_player));
            showView(findViewById(R.id.switch_player));
            hideView(findViewById(R.id.game_layout));
            info.setText(" ");
            for(Button button :getButtonList()){
                button.setText("_");
                button.setEnabled(true);

            }
            getButtonList().clear();
    }


    List<Button> buttonList = new ArrayList<>();

    public void addButton(Button button){
        buttonList.add(button);

    }
    public List<Button> getButtonList(){
        return buttonList;
    }

}