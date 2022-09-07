package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout gameLayout;
    List<Button> buttonListPl1 = new ArrayList<>();
    List<Button> buttonListPl2 = new ArrayList<>();
    List<ButtonNodeSequence> bns = new ArrayList<>();
    String player1_id;
    String player2_id;
    ToggleButton switchPlayer;
    TextView info;
    int turn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout=findViewById(R.id.game_layout);
        switchPlayer= findViewById(R.id.switch_player);
        player1_id="";

        player2_id="";
        info= ((TextView)findViewById(R.id.info));
        hideView(gameLayout);

        turn =0;
        setRules();
    }

    public void setPlayerID(){
        ((Button)findViewById(R.id.start_button)).setEnabled(true);
        player1_id = (String) switchPlayer.getText();
        if(Objects.equals(player1_id, "X")){
            player2_id="O";
        }else{
            player2_id="X";
        }


    }
    public void start_game(View view){
        info.setText("");
        turn=0;
        setPlayerID();
        switchPlayer.setVisibility(View.INVISIBLE);

        ((TextView)findViewById(R.id.set_player)).setVisibility(View.INVISIBLE);
        showView(gameLayout);
        ((Button)findViewById(R.id.start_button)).setEnabled(false);
    }
    public void showView(View view){
        view.setVisibility(View.VISIBLE);
    }
    public void hideView(View view){
        view.setVisibility(View.INVISIBLE);
    }

    public void game(View view){

        Button clickedButton = (Button) view;


        if(turn%2==0){
            clickedButton.setText(player1_id);
            buttonListPl1.add(clickedButton);
            info.setText("Player2 turn "+ player2_id);

        }else{
            clickedButton.setText(player2_id);
            buttonListPl2.add(clickedButton);
            info.setText("Player1 turn "+player1_id);

        }
        clickedButton.setEnabled(false);

        //Method informing and checking who won.
        info = checkIfIsWinner(clickedButton, clickedButton.getText());
        turn++;
        if(turn==9) {
            if (buttonListPl1.stream().allMatch(button -> !button.isEnabled()) ||
                    buttonListPl2.stream().allMatch(button -> !button.isEnabled())) {
                info.setText("No winner!");
                Log.d("Winner", "None ");
                hideView(findViewById(R.id.game_layout));
                showView(findViewById(R.id.switch_player));
                showView(findViewById(R.id.set_player));
                setPlayerID();
                activateButtons();
            }
        }
    }

    public int getButtonId(Button clickedButton){
        String bName = String.valueOf(clickedButton.getId());
        int buttonId = Integer.parseInt(bName.substring(8,10));
        return buttonId;
    }

    Set<int[]> nodeHolderSet = new HashSet<>();
    List<Button> winnerlist = new ArrayList<>();

    public TextView checkIfIsWinner(Button clickedButton, CharSequence symbol) {//B1
        TextView info = (TextView) findViewById(R.id.info);
        String playerSymbol = String.valueOf(symbol);//X
        int playerId = getButtonId(clickedButton);//18=B1

        if (buttonListPl1.stream().filter(button -> button.getText() == playerSymbol).findAny().isPresent()) {
            winnerlist = buttonListPl1;
            Log.d("addedPl1", playerSymbol);
        } else {
            winnerlist = buttonListPl2;
            Log.d("addedPl2", playerSymbol);

        }

        for(ButtonNodeSequence bNS : bns) {//18
            if (playerSymbol.matches(bNS.getSymbol())) {
                int[] nodeHolder = new int[3];
                if (playerId == bNS.getId()) {
                    nodeHolder[0] = playerId;
                    nodeHolder[1] = bNS.getPrevNode().getId();
                    nodeHolder[2] = bNS.getNextNode().getId();

                } else if (playerId == bNS.getNextNode().getId()) {
                    nodeHolder[0] = playerId;
                    nodeHolder[1] = bNS.getId();
                    nodeHolder[2] = bNS.getPrevNode().getId();

                } else if (playerId == bNS.getPrevNode().getId()) {
                    nodeHolder[0] = playerId;
                    nodeHolder[1] = bNS.getId();
                    nodeHolder[2] = bNS.getNextNode().getId();
                }


                nodeHolderSet.add(nodeHolder);
            }
        } Log.d("CheckingRules", "CheckedRules");
        List<Integer> winnerIds = new ArrayList<>();
        for (Button b : winnerlist) {
            winnerIds.add(getButtonId(b));
        }

        winnerIds.stream().sorted();
        for(int[] i: nodeHolderSet){
            int winner=0;
            for(int j=0;j<i.length;j++) {
                for(Integer win : winnerIds) {
                    if((win==Integer.valueOf(i[j]))) {
                        winner++;

                    } System.out.println(winner);
                }
                if(winner==3){
                    info.setText("Player " + playerSymbol + " won!");
                    Log.d("Winner", "Player " + playerSymbol);
                    hideView(findViewById(R.id.game_layout));
                    showView(findViewById(R.id.switch_player));
                    showView(findViewById(R.id.set_player));
                    setPlayerID();
                    activateButtons();
                    break;
                }
                }

            }


        nodeHolderSet.clear();
       return info;
    }




    public void activateButtons(){
        for(Button button :buttonListPl1){
            button.setText("_");
            button.setEnabled(true);
        }
        for(Button button :buttonListPl2){
            button.setText("_");
            button.setEnabled(true);
        }
        buttonListPl1.clear();
        buttonListPl2.clear();

    }

   public void setRules(){

       //winning matches 18-19-20  18-21-24 18-22-26 19-22-25 20-23-26 20-22-24 21-22-23 24-25-26
       ButtonNodeSequence rule1 = new ButtonNodeSequence(18,"X");
       rule1.setNextNode(new ButtonNodeSequence(19,"X"));
       rule1.setPrevNode(new ButtonNodeSequence(20,"X"));
       bns.add(rule1);

       ButtonNodeSequence rule2 = new ButtonNodeSequence(18,"X");
       rule2.setNextNode(new ButtonNodeSequence(21,"X"));
       rule2.setPrevNode(new ButtonNodeSequence(24,"X"));
       bns.add(rule2);

       ButtonNodeSequence rule3 = new ButtonNodeSequence(18,"X");
       rule3.setNextNode(new ButtonNodeSequence(22,"X"));
       rule3.setPrevNode(new ButtonNodeSequence(26,"X"));
       bns.add(rule3);

       ButtonNodeSequence rule4 = new ButtonNodeSequence(19,"X");
       rule4.setNextNode(new ButtonNodeSequence(22,"X"));
       rule4.setPrevNode(new ButtonNodeSequence(25,"X"));
       bns.add(rule4);

       ButtonNodeSequence rule5 = new ButtonNodeSequence(20,"X");
       rule5.setNextNode(new ButtonNodeSequence(23,"X"));
       rule5.setPrevNode(new ButtonNodeSequence(26,"X"));
       bns.add(rule5);

       ButtonNodeSequence rule6 = new ButtonNodeSequence(20,"X");
       rule6.setNextNode(new ButtonNodeSequence(22,"X"));
       rule6.setPrevNode(new ButtonNodeSequence(24,"X"));
       bns.add(rule6);

       ButtonNodeSequence rule7 = new ButtonNodeSequence(21,"X");
       rule7.setNextNode(new ButtonNodeSequence(22,"X"));
       rule7.setPrevNode(new ButtonNodeSequence(23,"X"));
       bns.add(rule7);

       ButtonNodeSequence rule8 = new ButtonNodeSequence(24,"X");
       rule8.setNextNode(new ButtonNodeSequence(25,"X"));
       rule8.setPrevNode(new ButtonNodeSequence(26,"X"));
       bns.add(rule8);

       ButtonNodeSequence rule11 = new ButtonNodeSequence(18,"O");
       rule11.setNextNode(new ButtonNodeSequence(19,"O"));
       rule11.setPrevNode(new ButtonNodeSequence(20,"O"));
       bns.add(rule11);

       ButtonNodeSequence rule12 = new ButtonNodeSequence(18,"O");
       rule12.setNextNode(new ButtonNodeSequence(21,"O"));
       rule12.setPrevNode(new ButtonNodeSequence(24,"O"));
       bns.add(rule12);

       ButtonNodeSequence rule13 = new ButtonNodeSequence(18,"O");
       rule13.setNextNode(new ButtonNodeSequence(22,"O"));
       rule13.setPrevNode(new ButtonNodeSequence(26,"O"));
       bns.add(rule13);

       ButtonNodeSequence rule14 = new ButtonNodeSequence(19,"O");
       rule14.setNextNode(new ButtonNodeSequence(22,"O"));
       rule14.setPrevNode(new ButtonNodeSequence(25,"O"));
       bns.add(rule14);


       ButtonNodeSequence rule15 = new ButtonNodeSequence(20,"O");
       rule15.setNextNode(new ButtonNodeSequence(23,"O"));
       rule15.setPrevNode(new ButtonNodeSequence(26,"O"));
       bns.add(rule15);

       ButtonNodeSequence rule16 = new ButtonNodeSequence(20,"O");
       rule16.setNextNode(new ButtonNodeSequence(22,"O"));
       rule16.setPrevNode(new ButtonNodeSequence(24,"O"));
       bns.add(rule16);

       ButtonNodeSequence rule17 = new ButtonNodeSequence(21,"O");
       rule17.setNextNode(new ButtonNodeSequence(22,"O"));
       rule17.setPrevNode(new ButtonNodeSequence(23,"O"));
       bns.add(rule17);

       ButtonNodeSequence rule18 = new ButtonNodeSequence(24,"O");
       rule18.setNextNode(new ButtonNodeSequence(25,"O"));
       rule18.setPrevNode(new ButtonNodeSequence(26,"O"));
       bns.add(rule18);
   }
}