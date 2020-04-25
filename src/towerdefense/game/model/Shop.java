package towerdefense.game.model;

import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.interfaces.Buyable;
import towerdefense.game.map.Map;
import towerdefense.game.towers.GenericTower;
import towerdefense.game.towers.CanonTower;
import towerdefense.game.towers.GlueTower;

public class Shop {
    public enum Type {GENERIC_TOWER, GLUE_TOWER, CANON_TOWER}

    public Buyable buy(String type, Map map) {
        Buyable res = null;
        if (!type.equals("Tower")) {
            res = (Buyable) getInstance(type, map);
        }
        return res;
    }

    public Buyable getInstance(Type type, Map map) {
        Buyable res = null;
        switch (type){
            //Tower
            case "standardtower": res = new GenericTower(map, 3, 10,2); break;
            case "rapidtower": res = new GenericTower(map,2,10,5); break;
            case "longrangetower": res = new GenericTower(map,5,30,1); break;
            case "canontower": res = new CanonTower(map, 200, 10); break;
            case "gluetower": res = new GlueTower(map,300); break;

            //Goldmine
            case "goldmine": res = new GoldMine(map); break;
            default : System.out.println("Invalid Buyable type");
        }
        return res;
    }

}
