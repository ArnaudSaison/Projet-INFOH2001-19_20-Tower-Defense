package towerdefense.game.model;

import towerdefense.game.Buyable;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.towers.*;

public class Shop {
    public enum Type {GENERIC_TOWER,RAPID_FIRE_TOWER, LONG_RANGE_TOWER,STRONG_ATTACK_TOWER, GLUE_TOWER, CANON_TOWER, GOLDMINE}

    public Buyable getInstance(Type type, Map map) {
        Buyable res = null;
        switch (type){
            //Towers
            case GENERIC_TOWER: res = new GenericTower(map); break;
            case RAPID_FIRE_TOWER: res = new RapidFireTower(map,5,110); break;
            case LONG_RANGE_TOWER: res = new LongRangeTower(map,20,120); break;
            case STRONG_ATTACK_TOWER: res = new StrongAttackTower(map,10,130); break;
            case CANON_TOWER: res = new CanonTower(map,3,5,200 ); break;
            case GLUE_TOWER: res = new GlueTower(map,3,2,250); break;

            //Goldmine
            case GOLDMINE: res = new GoldMine(map,1,2,3,4); break;
            default : System.out.println("Invalid Buyable type");
        }
        return res;
    }
}
