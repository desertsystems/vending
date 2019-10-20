package com.oracle.vending;

import com.oracle.vending.model.Coin;
import com.oracle.vending.model.TransactionType;
import com.oracle.vending.service.Transaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;


@SpringBootTest
public class VendingMachineServiceTest {

    @Autowired
    private VendingMachine vendingMachine;
    private Coin coin;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void init() {
        vendingMachine = new VendingMachine();
        //vendingMachine.load("coins.json"); // load coions from parameter file
        vendingMachine.load(); // load coins from properties file

        coin = new Coin();
        coin.setValue(0.50f);
    }

    @Test
    public void testInitialisation() {
        assertTrue(vendingMachine.getTotal() > 0.00f);
    }

    @Test
    public void testDeposit() {
        Float totalBefore = vendingMachine.getTotal();
        Float promise = totalBefore + coin.getValue();

        Transaction transaction = new Transaction();
        transaction.addCoin(coin);

        boolean status = vendingMachine.deposit(transaction);
        Float totalAfter = vendingMachine.getTotal();

        assertTrue(totalAfter.equals(promise));
    }

    @Test
    public void testRemove() {
        Float sum = 7.77f;
        Float totalBefore = vendingMachine.getTotal();
        Float promise = totalBefore - sum;

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.REMOVE);
        transaction.setSum(sum);

        transaction = vendingMachine.remove(transaction);
        Float totalAfter = vendingMachine.getTotal();

        assertTrue(totalAfter.equals(promise));
    }

    @Test
    public void testInsufficientChange() {
        Float sum = 777.77f;

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.REMOVE);
        transaction.setSum(sum);

        transaction = vendingMachine.remove(transaction);

        assertTrue(transaction.getSum() > 0.00f);
    }
}
