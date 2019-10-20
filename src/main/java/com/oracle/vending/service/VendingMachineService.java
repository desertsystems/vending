package com.oracle.vending.service;

/**
 * Vending Machine Service interface
 */
public interface VendingMachineService {

    /**
     * Initialises coin change repositry
     * @param transaction
     * @return boolean
     */
    public boolean initialise(Transaction transaction);

    /**
     * Deposites one to many coins into the change repository
     * @param transaction
     * @return boolean
     */
    public boolean deposit(Transaction transaction);

    /**
     * Removes a given sum from the change repository
     * @param transaction
     * @return Transaction
     */
    public Transaction remove(Transaction transaction);

    /**
     * Returns current change from repository
     * @return Transaction
     */
    public Transaction getChange();

    /**
     * Returns current total from repository
     * @return Float
     */
    public Float getTotal();

    /**
     * Returns current status of Vending Machine
     * @return String
     */
    public String getStatus();

    /**
     * Sets current status of Vending Machine
     * @param status
     */
    public void setStatus(String status);
}
