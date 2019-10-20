package com.oracle.vending;

import com.oracle.vending.model.Coin;
import com.oracle.vending.model.Menu;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * loads coin and menu configurations from properties
 */
@Component
@ConfigurationProperties("vending")
public class VendingMachineProperties {

    private List<Coin> coinlist = new ArrayList<Coin>();
    private List<Menu> mainmenu = new ArrayList<Menu>();
    private List<Menu> coinmenu = new ArrayList<Menu>();
    private List<Menu> footermenu = new ArrayList<Menu>();


    public List<Coin> getCoinlist() {
        return coinlist;
    }

    public void setCoinlist(List<Coin> coinlist) {
        this.coinlist = coinlist;
    }

    public List<Menu> getMainmenu() {
        return mainmenu;
    }

    public void setMainmenu(List<Menu> mainmenu) {
        this.mainmenu = mainmenu;
    }

    public List<Menu> getCoinmenu() {
        return coinmenu;
    }

    public void setCoinmenu(List<Menu> coinmenu) {
        this.coinmenu = coinmenu;
    }

    public List<Menu> getFootermenu() {
        return footermenu;
    }

    public void setFootermenu(List<Menu> footermenu) {
        this.footermenu = footermenu;
    }
}
