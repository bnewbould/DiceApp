package com.jc210391.diceapp;

import android.content.res.Resources;

import java.util.ArrayList;

public class AdditiveRoll implements Roll {

    private int result;
    private Integer[] sequence;
    private int diceUsed, diceType;

    AdditiveRoll(int diceUsed, int diceType, ArrayList<Integer> sequence) {
        this.diceUsed = diceUsed;
        this.diceType = diceType;
        this.sequence = new Integer[sequence.size()];
        this.sequence = sequence.toArray(this.sequence);
        for (Integer aSequence : this.sequence) {
            result += aSequence;
        }
    }

    @Override
    public String getResult() {
        return String.valueOf(result);
    }

    @Override
    public String getSequence() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < sequence.length; i++) {
            sb.append(sequence[i]);
            if (i != sequence.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");

        return sb.toString();
    }

    @Override
    public String getPool() {
        return String.format(Resources.getSystem().getString(R.string.dice_notation), diceUsed, diceType);
    }
}
