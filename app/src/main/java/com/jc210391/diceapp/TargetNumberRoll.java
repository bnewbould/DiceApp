package com.jc210391.diceapp;

import android.content.res.Resources;

import java.util.ArrayList;

public class TargetNumberRoll implements Roll {

    private int result;
    private Boolean[] sequence;
    private int diceUsed, diceType, targetNumber;

    TargetNumberRoll(int diceUsed, int diceType, int targetNumber, ArrayList<Integer> sequence) {
        this.result = 0;
        this.diceUsed = diceUsed;
        this.diceType = diceType;
        this.targetNumber = targetNumber;
        this.sequence = new Boolean[sequence.size()];
        for (int i = 0; i < this.sequence.length; i++) {
            this.sequence[i] = sequence.get(i) >= this.targetNumber;
            if (this.sequence[i]) {
                result++;
            }
        }
    }

    @Override
    public String getResult() {
        return Resources.getSystem().getQuantityString(R.plurals.successes, result, result);
    }

    @Override
    public String getSequence() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < sequence.length; i++) {
            sb.append(sequence[i] ? Resources.getSystem().getString(R.string.success) : Resources.getSystem().getString(R.string.failure));
            if (i != sequence.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");

        return sb.toString();
    }

    @Override
    public String getPool() {
        return String.format(Resources.getSystem().getString(R.string.dice_notation_target_number), diceUsed, diceType, targetNumber);
    }
}
