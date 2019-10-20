package com.oracle.vending;

import com.oracle.vending.model.Coin;
import com.oracle.vending.model.Menu;
import com.oracle.vending.model.OptionType;
import com.oracle.vending.model.TransactionType;

import java.util.HashMap;

/**
 * Deposit menu subclass
 * renders specific options and handles related input
 */
public class VendingMachineDepositMenu extends VendingMachineMenu {

    public VendingMachineDepositMenu() {
    }

    @Override
    public void options() {
        HashMap<Coin, Integer> change = vendingMachine.getChange();
        divider();

        for(Menu menu : vendingMachine.getCoinMenu()) {
            for(Coin coin : change.keySet()) {
                if(coin.getOption().equals(menu.getOption())) {
                    ln(menu.getText() +CHANGE_TAB+"["+change.get(coin)+"]");
                }
            }
        }

        ln(CHANGE_TAB+"[change]");

    }

    @Override
    public void handleInput() {
        Integer input = super.input();

        if(input > 0 && input < 21) {
            transaction(input);
            navigator.menu(OptionType.DEPOSIT);

        } else super.handleInput(input);
    }

    /**
     * Maps selelcted menu option to coin
     * and sets transaction
     * @param option
     */
    private void transaction(Integer option) {
        for(Coin coin : vendingMachine.getCoins()) {
            if(coin.getOption().equals(option)) {
                transaction.setType(TransactionType.DEPOSIT);
                transaction.addCoin(coin);
            }
        }
    }
}
