package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;

import java.util.ArrayList;

public class NPCFactory {

    public NPC getInstance(String type, Map map, GameModel gameModel, ArrayList<Integer> specifications) {
        NPC res = null;

        //Lecture des attributs des NPCs:
        int health = specifications.get(0);
        int speed = specifications.get(1);
        int gooldLoot = specifications.get(2);

        //Increments speciaux propre à chaque type de NPC:
        int extraHealth = specifications.get(3) ;
        int extraSpeed = specifications.get(4);
        int extraGoldLoot = specifications.get(5);

        //Gestion des différents cas:
        switch (type){
            case "standardnpc": res = new StandardNPC(map, gameModel, health, speed, gooldLoot, type);
            break;
            case "rapidnpc": res = new StandardNPC(map, gameModel, health, speed+extraSpeed, gooldLoot+extraGoldLoot, type);
                break;
            case "superhealthnpc": res = new StandardNPC(map, gameModel, health+extraHealth, speed, gooldLoot+extraGoldLoot, type);
                break;
            case "explosiveresistantnpc": res = new ExplosiveResistantNPC(map, gameModel, health, speed, gooldLoot);
                break;
            case "glueresistantnpc": res = new GlueResistantNPC(map, gameModel, health, speed, gooldLoot);
                break;

            default : System.out.println("Invalid NPC type");
        }
        return res;
    }
}
