package com.oracle.vending;

import com.oracle.vending.model.OptionType;
import com.oracle.vending.service.Transaction;
import org.springframework.stereotype.Component;

/**
 * Handles User interaction with menu and calls menu factory
 */
@Component
public class VendingMachineNavigator {

    private VendingMachineMenuFactory factory;
    private VendingMachine vendingMachine;
    private Transaction transaction;


    public VendingMachineNavigator(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        factory = new VendingMachineMenuFactory();
        transaction = new Transaction();
    }

    public void menu(OptionType type) {
        factory.setOptionType(type);
        VendingMachineMenu menu = factory.getObject();
        menu.setVendingMachine(vendingMachine);
        menu.setNavigator(this);
        menu.setTransaction(transaction);
        menu.render();
    }

    public void menu() {
        menu(OptionType.MAIN);
    }
}
