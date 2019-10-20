package com.oracle.vending;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.vending.model.Coin;
import com.oracle.vending.model.Menu;
import com.oracle.vending.service.Transaction;
import com.oracle.vending.service.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * Vending Machine Client
 * Calles VendingMachineService for available User transactions
 */
@Component
public class VendingMachine {

    private VendingMachineService vendingMachineService;

    private List<Coin> defaultChange; // default change load from properties
    private String coinFile; // coin file name send as runtime parameter
    private List<Menu> mainMenu; // hold list of menu items
    private List<Menu> coinMenu; // hold list of defined coins and values
    private List<Menu> footerMenu; // holds list of common menu items Exit|Cancel|Confirm
    private String status;


    /**
     * load properties and defined lists
     * @param vendingMachineProperties
     */
    @Autowired
    public void setVendingMachineProperties(VendingMachineProperties vendingMachineProperties) {
        defaultChange = vendingMachineProperties.getCoinlist();
        mainMenu = vendingMachineProperties.getMainmenu();
        coinMenu = vendingMachineProperties.getCoinmenu();
        footerMenu = vendingMachineProperties.getFootermenu();
    }

    @Autowired
    public void setService(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    /**
     * load and initialise from properties file
     */
    public void load() {
        Transaction transaction = new Transaction();
        transaction.setChange(defaultChange);
        vendingMachineService.initialise(transaction);
        run();
    }

    /**
     * Initialises Vending Machine
     * @param coinFile
     */
    public void load(String coinFile) {
        this.coinFile = coinFile;

        Transaction transaction = new Transaction();
        transaction.setChange(defaultChange);

        if(coinFile != null) {

            try {
                ObjectMapper map = new ObjectMapper();
                List<Coin> change = map.readValue(new File(coinFile), new TypeReference<List<Coin>>(){});

                if(change != null) transaction.setChange(change);
                vendingMachineService.initialise(transaction);

            } catch(IOException ioException) {
                vendingMachineService.initialise(transaction);
            }
        } else {
            vendingMachineService.initialise(transaction);
        }

        run();
    }

    /**
     * Resets Vending Machine change
     */
    public void reload() {
        load(coinFile);
    }

    /**
     * loads and runs interactive menus
     */
    private void run() {
        VendingMachineNavigator navigator = new VendingMachineNavigator(this);
        navigator.menu();
    }

    /**
     * Gets current change from service
     * @return change
     */
    public HashMap<Coin, Integer> getChange() {
        return vendingMachineService.getChange().getCoins();
    }

    /**
     * Deposites transaction amount to service
     * @param transaction
     * @return boolean
     */
    public boolean deposit(Transaction transaction) {
        return vendingMachineService.deposit(transaction);
    }

    /**
     * Removes transacted amount from service
     * @param transaction
     * @return Transaction
     */
    public Transaction remove(Transaction transaction) {
        return vendingMachineService.remove(transaction);
    }

    /**
     * Gets total change from service
     * @return Float
     */
    public Float getTotal() {
        return vendingMachineService.getTotal();
    }

    /**
     * Gets current status from service
     * @return status
     */
    public String getStatus() {
        return vendingMachineService.getStatus();
    }

    /**
     * Sets status on service
     * @param status
     */
    public void setStatus(String status) {
        vendingMachineService.setStatus(status);
    }

    /**
     * Returns list of coin configurations
     * @return List<Coin>
     */
    public List<Coin> getCoins() {
        return defaultChange;
    }

    /**
     * Returns list of menu configurations
     * for main menu
     * @return List<Menu>
     */
    public List<Menu> getMainMenu() {
        return mainMenu;
    }

    /**
     * Returns list of menu configurations
     * for deposit menu
     * @return List<Menu>
     */
    public List<Menu> getCoinMenu() {
        return coinMenu;
    }

    /**
     * Returns list of menu configurations
     * for remove menu
     * @return List<Menu>
     */
    public List<Menu> getFooterMenu() {
        return footerMenu;
    }
}
