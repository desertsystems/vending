package com.oracle.vending.model;

/**
 * Menu model is populated from properties file
 * holds option id and text label for menus
 */
public class Menu {

    private Integer option;
    private String text;


    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "option=" + option +
                ", text='" + text + '\'' +
                '}';
    }
}
