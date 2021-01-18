package com.jc210391.diceapp;

import java.io.Serializable;

public interface Roll extends Serializable {

    String getResult();

    String getSequence();

    String getPool();

}
