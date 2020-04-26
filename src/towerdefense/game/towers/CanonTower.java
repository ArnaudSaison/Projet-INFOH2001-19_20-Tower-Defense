package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;

public class CanonTower extends Tower {
    public CanonTower(Map map, int price, int damageDeal){
        Position position = new Position(map);
        level = 1;
        this.price = price;
        priceIncrement = 5*level;
        range = 20;//.................................en mètre.
        fireRate = 4;//...............................coups/seconde.
        this.damageDeal = damageDeal;//...............en point de vie.
        targets = new ArrayList<NPC>();
        KIATargets = new ArrayList<NPC>();
        maxTargetNumber = 5;//....................... traite une jusqu'à 5 cibles.
    }

    //******traitement des cibles*******

    public ArrayList<NPC> hit(ArrayList<NPC> npcs) {
        this.targetAcquisition(npcs);
        for (NPC target : targets) {
            if (target.getType() != "ExplosiveResistant"){
                target.decreaseHealth(damageDeal);
                if (target.decreaseHealth(damageDeal) == "is dead") {
                    KIATargets.add(target);
                }
            }
        }
        return KIATargets;
    }

    //*******Passage de niveau******
    public void levelUp(){
        if (canBeLeveledUp()){
            price=+ priceIncrement;
            maxTargetNumber++;
            damageDeal++;
        }
    }
}
