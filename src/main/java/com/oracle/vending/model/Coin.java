package com.oracle.vending.model;

/**
 * Coin model
 * Hold value, lable and reset amound
 * is populated from the properties file
 * or from comman line arguments to the VendingMchineApplication client
 */
public class Coin implements Comparable<Coin> {

    private Integer option;
    private String label;
    private String type;
    private Float value;
    private Integer reset;


    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getReset() {
        return reset;
    }

    public void setReset(Integer reset) {
        this.reset = reset;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "option=" + option +
                ", label='" + label + '\'' +
                ", type=" + type +
                ", value=" + value +
                ", reset=" + reset +
                '}';
    }

    /**
     * overrides compareTo to order coin list by Float value
     * @param coin
     * @return
     */
    @Override
    public int compareTo(Coin coin) {
        if(option.equals(coin.getOption())) return 0;
        return (value < coin.getValue()) ? -1 : 1;
    }
}