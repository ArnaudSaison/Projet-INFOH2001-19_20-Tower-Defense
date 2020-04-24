package towerdefense.game.model;

import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.interfaces.Buyable;
import towerdefense.game.towers.Tower;

public class Shop {

    public Buyable buy(String type) {
        Buyable res = null;
        if (!type.equals("Tower")) {
            res = (Buyable) getInstance(type);
        }
        return res;
    }

    public Buyable getInstance(String type) {
        Buyable res = null;
        switch (type.toLowerCase()){
            //Tower
            case "tower1": res = new Tower(); break;
            case "tower2": res = new Tower(); break;
            case "tower3": res = new Tower(); break;
            case "tower4": res = new Tower(); break;
            case "tower5": res = new Tower(); break;
            //Goldmine
            case "goldmine": res = new GoldMine(); break;
            default : System.out.println("Invalid Buyable type");
        }
        return res;
    }

}
