package com.oracle.vending;

import com.oracle.vending.model.TransactionType;

import java.util.Scanner;

/**
 * Remove menu subclass
 * renders specific options and handles related input
 */
public class VendingMachineRemoveMenu extends VendingMachineMenu {

    private Float sum;


    public VendingMachineRemoveMenu() {
    }

    @Override
    public void render() {
        header();
        handleInput();
    }

    @Override
    public void confirm() {
        ln("sum confirm...");
        vendingMachine.setStatus("Remove Confirm/Cancel?");
        header();
        footer();
        super.handleInput(input());
    }

    @Override
    public void handleInput() {
        Scanner scan = new Scanner(System.in);
        divider();
        xln(PADDING+"enter sum: ");

        if(scan.hasNextFloat()) {
            sum = scan.nextFloat();
            transaction.setType(TransactionType.REMOVE);
            transaction.setSum(sum);
            confirm();

        } else {
            navigator.menu(optionType);
        }
    }
}
