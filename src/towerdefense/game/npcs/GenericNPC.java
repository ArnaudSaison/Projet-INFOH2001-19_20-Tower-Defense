package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class GenericNPC extends NPC {
    public GenericNPC(Map map, int health, int goldloot, int speed) {
        Position position = new Position(map);
        this.health = health;
        this.goldLoot = goldloot;
        this.speed = speed;
    }

    public String getType(){return "Generic";}
}