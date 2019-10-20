package com.oracle.vending.strategy;

import com.oracle.vending.exception.InsufficientChangeException;
import com.oracle.vending.model.Coin;
import com.oracle.vending.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * Defines algorithm for removing change from repository
 * by iteration repository in descending coin value
 */
@Component
public class RemoveByDescendingCoinValue implements RemoveStrategy {

    private CoinRepository repository;

    public RemoveByDescendingCoinValue(CoinRepository repository) {
        this.repository = repository;
    }

    @Override
    public void remove(Float sum) throws InsufficientChangeException {
        if(sum > repository.getTotal()) {
            throw new InsufficientChangeException(sum, repository.getTotal());

        } else {

            LinkedHashMap<Coin, Integer> change = repository.getChange();

            // looping coins from high value to low, until sum is depleted
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
                throw new com.oracle.vending.exception.InsufficientChangeException(sum, repository.getTotal());
            } else {
                // commit to repository
                repository.setChange(change);
            }
        }
    }
}
