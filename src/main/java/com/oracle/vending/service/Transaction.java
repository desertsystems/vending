package com.oracle.vending.service;

import com.oracle.vending.model.Coin;
import com.oracle.vending.model.TransactionType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Transaction class used to hold transaction values
 * and getters and setters between the client and Service
 */
@Component
public class Transaction {

    private Float sum;
    private List<Coin> change;
    private HashMap<Coin, Integer> coins;
    private Float total = 0.00f;
    private TransactionType type;

    public Transaction() {
        reset();
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
        total += sum;
    }

    public List<Coin> getChange() {
        return change;
    }

    public void setChange(List<Coin> change) {
        if(change != null) {
            this.change = change;
            for(Coin coin : change) total += coin.getValue();
        }
    }

    public HashMap<Coin, Integer> getCoins() {
        return coins;
    }

    public void setCoins(HashMap<Coin, Integer> coins) {
        this.coins = coins;
        for(Coin coin : coins.keySet()) total += coin.getValue() * coins.get(coin);
    }

    public void addCoin(Coin coin) {
        if(coins.get(coin) != null) coins.put(coin, coins.get(coin) + 1);
        else coins.put(coin, 1);
        total += coin.getValue();
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void reset() {
        sum = 0.00f;
        change = new ArrayList<Coin>();
        coins = new HashMap<Coin, Integer>();
        total = 0.00f;
        type = TransactionType.INITIATE;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sum=" + sum +
                ", change=" + change +
                ", coins=" + coins +
                ", total=" + total +
                ", type=" + type +
                '}';
    }
}