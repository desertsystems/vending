package com.oracle.vending;

import com.oracle.vending.model.OptionType;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


/**
 * Menu factory
 * Determines Menu type based on option type
 * Called from menu navigator
 */
@Component
public class VendingMachineMenuFactory implements FactoryBean<VendingMachineMenu> {

    private OptionType optionType;


    @Override
    public VendingMachineMenu getObject() {
        VendingMachineMenu menu;

        if(optionType == null ) menu = new VendingMachineMainMenu();
        else if(optionType.equals(OptionType.MAIN)) menu = new VendingMachineMainMenu();
        else if(optionType.equals(OptionType.DEPOSIT)) menu = new VendingMachineDepositMenu();
        else if(optionType.equals(OptionType.REMOVE)) menu = new VendingMachineRemoveMenu();
        else menu = new VendingMachineMainMenu();

        return menu;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    @Override
    public Class<?> getObjectType() {
        return VendingMachineMenu.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
