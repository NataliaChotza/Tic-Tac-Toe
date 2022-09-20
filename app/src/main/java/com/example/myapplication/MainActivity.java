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
        setTitle("Tic-Tac-Toe");
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

    public int getButtonId(Button button){
        String bName = String.valueOf(button.getId());
        int buttonId = Integer.parseInt(bName.substring(8,10));
        System.out.println("ButtonID->" +buttonId);
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
            //if (playerSymbol.matches(bNS.getSymbol())) {
                int[] nodeHolder = new int[3];
                if (playerId == bNS.getId()) {
                    nodeHolder[0] = playerId;
                    nodeHolder[1] = bNS.getPrevNode();
                    nodeHolder[2] = bNS.getNextNode();

                } else if (playerId == bNS.getNextNode()) {
                    nodeHolder[0] = playerId;
                    nodeHolder[1] = bNS.getId();
                    nodeHolder[2] = bNS.getPrevNode();

                } else if (playerId == bNS.getPrevNode()) {
                    nodeHolder[0] = playerId;
                    nodeHolder[1] = bNS.getId();
                    nodeHolder[2] = bNS.getNextNode();
                }

                nodeHolderSet.add(nodeHolder);

           // }
        }
        for (int[] n: nodeHolderSet) {
            for(int i=0;i<n.length;i++){
            System.out.print(n[i]+" ");
        }
            System.out.println();
        }

         Log.d("CheckingRules", "CheckedRules");
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

   public void setRules() {

       //winning matches 18-19-20  18-21-24 18-22-26 19-22-25 20-23-26 20-22-24 21-22-23 24-25-26
       ButtonNodeSequence rule1 = new ButtonNodeSequence(19, 20, 21);
       bns.add(rule1);

       ButtonNodeSequence rule2 = new ButtonNodeSequence(19, 22, 25);
       bns.add(rule2);

       ButtonNodeSequence rule3 = new ButtonNodeSequence(19, 23, 27);
       bns.add(rule3);

       ButtonNodeSequence rule4 = new ButtonNodeSequence(20, 23, 26);
       bns.add(rule4);

       ButtonNodeSequence rule5 = new ButtonNodeSequence(21, 24, 27);
       bns.add(rule5);

       ButtonNodeSequence rule6 = new ButtonNodeSequence(21, 23, 25);
       bns.add(rule6);

       ButtonNodeSequence rule7 = new ButtonNodeSequence(22, 23, 24);
       bns.add(rule7);

       ButtonNodeSequence rule8 = new ButtonNodeSequence(25, 26, 27);
       bns.add(rule8);
   }
}