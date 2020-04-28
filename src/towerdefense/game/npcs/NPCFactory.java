package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;


public class NPCFactory {
    public enum Type {STANDARD_NPC, RAPID_NPC, SUPER_HEALTH_NPC, EXPLOSIVE_RESISTANT_NPC, GLUE_RESISTANT_NPC}

    public NPC getInstance(Type type, Map map, GameModel gameModel) {
        NPC res = null;
        switch (type){
            case STANDARD_NPC:_NPC: res = new StandardNPC(map,gameModel); break;
            case RAPID_NPC: res = new RapidNPC(map,gameModel,10000 ); break;
            case SUPER_HEALTH_NPC: res = new SuperHealthNPC(map,gameModel,150 ); break;
            case EXPLOSIVE_RESISTANT_NPC: res = new ExplosiveResistantNPC(map,gameModel,5 ); break;
            case GLUE_RESISTANT_NPC: res = new GlueResistantNPC(map,gameModel,7 ); break;

            default : System.out.println("Invalid NPC type");
        }
        return res;
    }
}
