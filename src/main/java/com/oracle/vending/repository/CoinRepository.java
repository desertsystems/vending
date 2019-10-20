package com.oracle.vending.repository;

import com.oracle.vending.model.Coin;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Repository to hold coins change for Vending Machine
 */
@Component
@Scope("singleton")
public class CoinRepository {

    private Float total = 0.00f;
    private LinkedHashMap<Coin, Integer> change;

    public CoinRepository() {

    }

    public CoinRepository(LinkedHashMap<Coin, Integer> change) {
        setChange(change);
    }

    public Float getTotal() {
        return round(total);
    }

    public LinkedHashMap<Coin, Integer> getChange() {
        return change;
    }

    public void setChange(LinkedHashMap<Coin, Integer> change) {
        this.change = change;
        total = 0.00f;
        calculateTotal();
    }

    private void calculateTotal() {
        for(Coin coin : change.keySet()) total = total + (coin.getValue() * change.get(coin));
    }

    public void addCoin(Coin coin) {
        if(change.get(coin) != null) change.put(coin, change.get(coin) +1);
        else change.put(coin, 1);

        total = total + coin.getValue();
    }

    public void removeCoin(Coin coin) {
        if(change.get(coin) > 0) {
            change.put(coin, change.get(coin) -1);
            total = total -coin.getValue();
        }
    }

    /**
     * round float amount to 2 decimal places
     * @param amount
     * @return Float
     */
    private Float round(Float amount) {
        return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
