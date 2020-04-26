package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;

public class GlueTower extends Tower {

    public GlueTower(Map map, int price) {
        Position position = new Position(map);
        level = 1;
        this.price = price;
        priceIncrement = 5 * level;
        damageDeal = 0;
        range = 20;//.................................en mètre.
        fireRate = 4;//...............................coups/seconde.
        targets = new ArrayList<NPC>();
        KIATargets = new ArrayList<NPC>();
        maxTargetNumber = 5;//....................... traite une jusqu'à 5 cibles.
    }

    //******Traitement des cibles*******

    private void targetAcquisition(ArrayList<NPC> npcs) {
        if (targets.size() < maxTargetNumber) {
            for (NPC npc : npcs) {
                if ((npc.getPos()).getDistance(position) <= range){
                    targets.add(npc);
                }
            }
        }
    }

    public ArrayList<NPC> hit(ArrayList<NPC> npcs) {
        this.targetAcquisition(npcs);
        for (NPC target : targets) {
            if (target.getType() != "GlueResistant") {
                target.glued();
            }
        }
        return KIATargets;
    }

    //******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            price = +priceIncrement;
            maxTargetNumber++;
            range++;
        }
    }
}