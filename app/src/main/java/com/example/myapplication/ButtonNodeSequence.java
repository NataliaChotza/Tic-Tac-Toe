package com.example.myapplication;

public class ButtonNodeSequence {
    protected int id;
    protected String symbol;
    protected ButtonNodeSequence nextNode,prevNode;
    public ButtonNodeSequence(int id,String symbol){
        setId(id);
        setSymbol(symbol);
    }
    public void setNextNode(ButtonNodeSequence nextNode){
        this.nextNode=nextNode;
    }
    public void setPrevNode(ButtonNodeSequence prevNode){
        this.prevNode=prevNode;
    }
    public ButtonNodeSequence getNextNode(){
        return nextNode;
    }
    public ButtonNodeSequence getPrevNode(){
        return prevNode;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }


}
