package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class GenericNPC extends NPC {
    public GenericNPC(Map map, int health, int goldloot, int speed) {
        super(Map map, int health, int goldloot, int speed); // TODO: régler constrcuteur
    }

    public String getType(){return "Generic";}
}
