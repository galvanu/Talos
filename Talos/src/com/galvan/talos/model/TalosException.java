package com.galvan.talos.model;

/**
 * Created by galvan on 4/11/15.
 */
public class TalosException extends Exception {

    private TalosException(){}

    public TalosException(String errorMsg){
        super(errorMsg);
    }
}
