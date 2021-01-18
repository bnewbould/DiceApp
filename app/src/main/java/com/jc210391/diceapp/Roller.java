package com.jc210391.diceapp;

import java.util.ArrayList;
import java.util.Random;

class Roller {
    private Random rand;

    Roller() {
        rand = new Random();
    }

    ArrayList<Integer> roll(int quantity, int sides) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < quantity; i++) {
            result.add(rand.nextInt(sides) + 1);
        }
        return result;
    }
}
