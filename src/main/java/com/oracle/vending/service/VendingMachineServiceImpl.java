package com.oracle.vending.service;

import com.oracle.vending.exception.InsufficientChangeException;
import com.oracle.vending.model.Coin;
import com.oracle.vending.repository.CoinRepository;
import com.oracle.vending.strategy.RemoveByAscendingCoinValue;
import com.oracle.vending.strategy.RemoveByDescendingCoinValue;
import com.oracle.vending.strategy.RemoveStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Vending Machine Service implementation class
 */
@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    private static final String DESCENDING = "descending";
    private static final String ASCENDING = "ascending";

    // loads coin remove strategy from properties file
    @Value("${vending.coin.remove.strategy}")
    private String strategy;

    @Autowired
    private CoinRepository repository;

    private String status;


    /**
     * Initialise vending machine with given change
     * @param transaction
     * @return boolean
     */
    public boolean initialise(Transaction transaction) {
        if(repository == null) repository = new CoinRepository();

        List<Coin> coins = transaction.getChange();
        Collections.reverse(coins);

        LinkedHashMap<Coin, Integer> change = new LinkedHashMap<Coin, Integer>();
        for(Coin coin : coins) change.put(coin, coin.getReset());

        repository.setChange(change);

        status = "initialised";
        return repository.getTotal() > 0.00f;
    }

    /**
     * Deposit one or many coins into the vending machine
     * @param transaction
     * @return boolean
     */
    public boolean deposit(Transaction transaction) {
        Float promise = repository.getTotal() + sum(transaction.getCoins());
        for(Coin coin : transaction.getCoins().keySet()) repository.addCoin(coin);

        if(repository.getTotal().equals(promise)) status = "Coins deposited";
        return repository.getTotal().equals(promise);
    }

    private Float sum(HashMap<Coin, Integer> coins) {
        Float sum = 0.00f;
        for(Coin coin : coins.keySet()) sum += coin.getValue();
        return sum;
    }

    /**
     * Remove coins totaling to a given sum
     * @param transaction
     * @return Transaction
     */
    public Transaction remove(Transaction transaction) {
        try {
            RemoveStrategy removeStrategy;

            // load defiled strategy from the properties file
            if(strategy == null) removeStrategy = new RemoveByDescendingCoinValue(repository);
            else if(strategy.equals(DESCENDING)) removeStrategy = new RemoveByDescendingCoinValue(repository);
            else if(strategy.equals(ASCENDING)) removeStrategy = new RemoveByAscendingCoinValue(repository);
            else removeStrategy = new RemoveByDescendingCoinValue(repository);

            removeStrategy.remove(transaction.getSum());
            transaction.reset();
            status = "Coins removed";

            return transaction;

        } catch(InsufficientChangeException insufficientChange) {
            return transaction;
        }
    }

    /**
     * returns current amount of change in the Vending Machine repository
     * @return Transaction
     */
    public Transaction getChange() {
        Transaction transaction = new Transaction();
        transaction.setCoins(repository.getChange());
        return transaction;
    }

    /**
     * returns current total sum of change in Vending Machine
     * @return total
     */
    public Float getTotal() {
        return repository.getTotal();
    }

    /**
     * returns current status of Vending Machine
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * setting statuse from client
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
