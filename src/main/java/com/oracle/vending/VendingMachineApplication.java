package com.oracle.vending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication
 * loads the interactive commanline client
 * to use the Vending Machine API
 */
@SpringBootApplication
public class VendingMachineApplication implements CommandLineRunner {

    @Autowired
    private VendingMachine vendingMachine;


    public VendingMachineApplication() {

    }

    @Override
    public void run(String[] args) {
        if(vendingMachine == null) vendingMachine = new VendingMachine();

        String file;
        if(args.length < 1) file = "coins.json";
        else file = args[0];

        vendingMachine.load(file);
    }

    /**
     * Take parameter at runtime to specify coins.json file
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(VendingMachineApplication.class, args);
    }
}
