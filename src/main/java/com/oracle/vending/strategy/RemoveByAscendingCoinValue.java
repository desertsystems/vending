package com.oracle.vending.strategy;

import com.oracle.vending.exception.InsufficientChangeException;
import com.oracle.vending.model.Coin;
import com.oracle.vending.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Defines algorithm for removing change from repository
 * by iteration repository in ascending coin value
 */
@Component
public class RemoveByAscendingCoinValue implements RemoveStrategy {

    private CoinRepository repository;

    public RemoveByAscendingCoinValue(CoinRepository repository) {
        this.repository = repository;
    }

    @Override
    public void remove(Float sum) throws InsufficientChangeException {
        if(sum > repository.getTotal()) {
            throw new InsufficientChangeException(sum, repository.getTotal());

        } else {

            LinkedHashMap<Coin, Integer> changex = repository.getChange();

            List<Coin> coins = new ArrayList<Coin>(changex.keySet());
            Collections.reverse(coins);

            LinkedHashMap<Coin, Integer> change = new LinkedHashMap<Coin, Integer>();
            for(Coin coin : coins) change.put(coin, changex.get(coin));

            // looping coins from low to high value
            for(Coin coin : change.keySet()) {

                int availableCoins = Math.round(sum/coin.getValue());
                if((availableCoins *coin.getValue()) > sum) availableCoins--;

                if(availableCoins < change.get(coin)) {
                    sum = sum - coin.getValue() *availableCoins; // subtract available coins from sum
                    change.put(coin, change.get(coin) -availableCoins); // set remaining change

                } else {
                    if(change.get(coin) > 0) {
                        sum = sum - coin.getValue() *change.get(coin); // use all the coins available
                        change.put(coin, 0);
                    }
                }

            }

            if(sum != 0.00f) {
                // rollback and cancel transaction
                throw new InsufficientChangeException(sum, repository.getTotal());
            } else {
                // commit to repository
                repository.setChange(change);
            }
        }
    }
}
