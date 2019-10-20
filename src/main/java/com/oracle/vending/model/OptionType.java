package com.oracle.vending.model;

/**
 * Options enum used to used to match menu options from the client interface
 */
public enum OptionType {
    EXIT(0),
    MAIN(-1),
    DEPOSIT(1),
    REMOVE(2),
    RESET(3),
    CANCEL(21),
    CONFIRM(22);

    protected Integer option;

    OptionType(Integer option) {
        this.option = option;
    }

    public Integer getOption() {
        return option;
    }
}
