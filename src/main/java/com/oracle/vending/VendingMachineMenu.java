package com.oracle.vending;

import com.oracle.vending.model.Menu;
import com.oracle.vending.model.OptionType;
import com.oracle.vending.service.Transaction;
import com.oracle.vending.model.TransactionType;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.System.exit;

/**
 * Abstract Menu class
 * Defines all common menu items and handles common input
 */
@Component
public abstract class VendingMachineMenu {

    public VendingMachine vendingMachine;
    public Transaction transaction;
    public VendingMachineNavigator navigator;

    public OptionType optionType;
    public static final String PADDING = " ";
    public static final String CHANGE_TAB = "\t\t\t";


    public VendingMachineMenu() {
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void setNavigator(VendingMachineNavigator navigator) {
        this.navigator = navigator;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    /**
     * renders menu to command prompt
     */
    public void render() {
        header();
        options();
        footer();
        handleInput();
    }

    /**
     * renders confirm dialog and handles related input
     */
    public void confirm() {
        vendingMachine.setStatus("Confirm/Cancel?");

        header();
        footer();
        handleInput();
    }

    /**
     * renders header section
     * holding total and transactional amount
     */
    public void header() {
        clear();
        ln("Vending Machine Change Application");

        divider();
        ln("Total in machine: \t£" + vendingMachine.getTotal());
        ln("Current transaction: \t£" + transaction.getTotal());
        ln("message: " + vendingMachine.getStatus());
    }

    public void options() {

    }

    /**
     * renders footer section
     */
    public void footer() {
        divider();
        xln(PADDING);
        int menuSize = vendingMachine.getFooterMenu().size();

        for(Menu menu : vendingMachine.getFooterMenu()) {
            xln(menu.getText());
            menuSize--;
            if(menuSize > 0) xln(" | ");
        }
    }

    /**
     * Takes User input
     * @return Integer
     */
    public Integer input() {
        try {
            Scanner scan = new Scanner(System.in);
            ln("");
            divider();
            xln(PADDING+"input menu number: ");
            return scan.nextInt();

        } catch(InputMismatchException inputException) {
            vendingMachine.setStatus("Invalid input.");
            return OptionType.CANCEL.getOption();
        }
    }

    /**
     * Handles User input
     */
    public void handleInput() {
        Integer input = input();
        handleInput(input);
    }

    /**
     * handles User input
     * @param input
     */
    public void handleInput(Integer input) {
        if(input.equals(OptionType.EXIT.getOption())) exit(0);

        if(input.equals(OptionType.CONFIRM.getOption())) {
            if(transaction.getType().equals(TransactionType.INITIATE)) vendingMachine.reload();
            if(transaction.getType().equals(TransactionType.DEPOSIT)) vendingMachine.deposit(transaction);
            if(transaction.getType().equals(TransactionType.REMOVE)) vendingMachine.remove(transaction);
            if(transaction.getType().equals(TransactionType.RESET)) {
                vendingMachine.reload();
            }

            transaction.reset();
        }

        navigator.menu(OptionType.MAIN);
    }

    public void divider() {
        ln("----------------------------------");
    }

    public void clear() {
        ln("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public void ln(String line) {
        System.out.println(PADDING + line);
    }

    public void xln(String txt) {
        System.out.print(txt);
    }
}
