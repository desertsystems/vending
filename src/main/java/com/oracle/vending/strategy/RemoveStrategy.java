package com.oracle.vending.strategy;

import com.oracle.vending.exception.InsufficientChangeException;

/**
 * Strategy interface
 * to remove a given sum of coins from Vending Machine
 */
public interface RemoveStrategy {

    /**
     * calls strategy type for remove chnage from repository
     * @param sum
     * @throws InsufficientChangeException
     */
    public void remove(Float sum) throws InsufficientChangeException;
}
