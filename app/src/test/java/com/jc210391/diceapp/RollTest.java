package com.jc210391.diceapp;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RollTest {

    static final int ARBITRARY_NUMBER = 1000;
    static final int ARBITRARY_SIZE = 20;

    @Test
    public void roller_Test() {
        Roller roller = new Roller();

        ArrayList<Integer> testList = roller.roll(ARBITRARY_NUMBER, ARBITRARY_SIZE);
        for (int i = 0; i < testList.size(); i++) {
            int result = testList.get(i);
            assertEquals(result > 0 && result <= ARBITRARY_SIZE, true);
        }
    }
}

/*
    I tried testing the AdditiveRoll and TargetNumberRoll classes using Mockito but
    their implementation doesn't play nicely with it.
    In order to test them I added context member fields in and pass the context in
    through the constructor to easily inject a mocked context, but
    unfortunately context references aren't serializable and break the
    implementation of the savedInstanceState history recall.

    So now those tests are dead. ¯\_(ツ)_/¯
*/