package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TicTacToeButton extends androidx.appcompat.widget.AppCompatButton {

    String symbol;
    public TicTacToeButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getSymbol() {
        return this.symbol;
    }
    public void setSymbol(String symbol){
        this.symbol=symbol;
    }
}
