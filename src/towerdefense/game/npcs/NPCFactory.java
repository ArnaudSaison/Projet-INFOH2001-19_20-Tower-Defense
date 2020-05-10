package towerdefense.game.npcs;

import towerdefense.game.map.GatePathTile;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.game.model.GameModel;
import towerdefense.game.waves.WaveFactory;

import java.util.ArrayList;

public class NPCFactory {

    public NPC getInstance(WaveFactory.NPCTypes type, Map map, GameModel gameModel, ArrayList<Integer> specifications, GatePathTile gatePathTile) {
        NPC res = null;

        //Lecture des attributs des NPCs:
        int health = specifications.get(0);
        int speed = specifications.get(1);
        int goldLoot = specifications.get(2);
        int healthLoot = specifications.get(3);

        //Gestion des différents cas:
        switch (type) {
            case STANDARD_NPC:
                res = new StandardNPC(map, gameModel, health, speed, goldLoot, healthLoot, gatePathTile, type);
                break;
            case RAPID_NPC:
                res = new StandardNPC(map, gameModel, health, speed, goldLoot, healthLoot, gatePathTile, type);
                break;
            case SUPER_HEALTH_NPC:
                res = new StandardNPC(map, gameModel, health, speed, goldLoot, healthLoot, gatePathTile, type);
                break;
            case EXPLOSIVE_RESISTANT_NPC:
                res = new ExplosiveResistantNPC(map, gameModel, health, speed, goldLoot, healthLoot, gatePathTile);
                break;
            case GLUE_RESISTANT_NPC:
                res = new GlueResistantNPC(map, gameModel, health, speed, goldLoot, healthLoot, gatePathTile);
                break;

//            default : System.out.println("Invalid NPC type");
        }
        return res;
    }
}