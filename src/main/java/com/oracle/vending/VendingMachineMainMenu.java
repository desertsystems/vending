package com.oracle.vending;

import com.oracle.vending.model.Menu;
import com.oracle.vending.model.OptionType;
import com.oracle.vending.model.TransactionType;
import org.springframework.stereotype.Component;

/**
 * Main menu subclass
 * renders specific options and handles related input
 */
@Component
public class VendingMachineMainMenu extends VendingMachineMenu {


    public VendingMachineMainMenu() {
    }

    @Override
    public void options() {
        divider();
        for(Menu menu : vendingMachine.getMainMenu()) ln(menu.getText());
    }

    @Override
    public void handleInput() {
        Integer input = super.input();

        if(input.equals(OptionType.DEPOSIT.getOption())) {
            transaction.setType(TransactionType.DEPOSIT);
            navigator.menu(OptionType.DEPOSIT);

        } else if(input.equals(OptionType.REMOVE.getOption())) {
            transaction.setType(TransactionType.REMOVE);
            navigator.menu(OptionType.REMOVE);

        } else if(input.equals(OptionType.RESET.getOption())) {
            transaction.setType(TransactionType.RESET);
            super.optionType = OptionType.RESET;
            confirm();

        } else {
            super.handleInput(input);
        }
    }
}
