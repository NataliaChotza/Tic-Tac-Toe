package com.example.myapplication;
import java.util.ArrayList;
import java.util.List;


public class ButtonNodeSequence {
    protected int id;
    protected int nextNode,prevNode;
    protected List<Integer>buttonNodeSequenceList = new ArrayList<>();


    public ButtonNodeSequence(int id,int nextNode,int prevNode){
        this.id=id;
        this.nextNode=nextNode;
        this.prevNode=prevNode;
        buttonNodeSequenceList.add(this.id);
        buttonNodeSequenceList.add(this.nextNode);
        buttonNodeSequenceList.add(this.prevNode);
    }

    public int getId(){
        return id;
    }

    public int getNextNode() {
        return nextNode;
    }

    public int getPrevNode() {
        return prevNode;
    }
    public List<Integer> getButtonNodeSequenceList() {
        return buttonNodeSequenceList;
    }
}
