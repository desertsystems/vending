package com.oracle.vending.exception;

/**
 * custom exception is thrown when insufficient change
 */
public class InsufficientChangeException extends Exception {

    Float sum;
    Float change;

    public InsufficientChangeException(Float sum, Float change) {
        this.sum = sum;
        this.change = change;
    }

    @Override
    public String toString() {
        return "InsufficientChangeException{" +
                "sum=" + sum +
                ", change=" + change +
                '}';
    }
}
