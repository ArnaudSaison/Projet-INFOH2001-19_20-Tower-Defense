package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;

public class GenericTower extends Tower{

    public GenericTower(Map map, int damageDeal, int range, int fireRate){
        Position position = new Position(map);
        level = 1;
        price = 100;
        priceIncrement = 3*level;
        this.range = range;//........................en mètre.
        this.fireRate = fireRate;//..................coups/seconde
        this.damageDeal = damageDeal;//..............en point de vie
        targets = new ArrayList<NPC>();
        KIATargets = new ArrayList<NPC>();
        maxTargetNumber = 1;//....................... traite une seule cible.
    }


    //*******Traitement des cibles*******

    public ArrayList<NPC> hit(ArrayList<NPC> npcs){
        for (NPC npc : npcs){
            if ((npc.getPos()).getDistance(position) <= range){
                npc.decreaseHealth(damageDeal);
                if (npc.decreaseHealth(damageDeal) == "is dead"){
                    KIATargets.add(npc);
                }
            }
        }
        return KIATargets;
    }

    //******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            price = +priceIncrement;
            range++;
            damageDeal++;
            fireRate++;
        }
    }
}
