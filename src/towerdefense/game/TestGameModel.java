package towerdefense.game;

import org.junit.Test;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.NPC;
import towerdefense.game.npcs.NPCFactory;
import towerdefense.game.towers.Tower;

import java.util.ArrayList;

public class TestGameModel {
    @Test
    public void testGoldMine(){
        //map bidon
        Tile tile = new Tile(1,1,2);
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile);
        Map map = new Map(tiles, 2.0,2.0,1,1,"Map Bidon");

        GoldMine mine = new GoldMine(map,300,50,100,20);
        mine.run();

    }

    @Test
    public void testNPCs(){
        //map bidon
        Tile tile = new Tile(1,1,2);
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile);
        Map map = new Map(tiles, 2.0,2.0,1,1,"Map Bidon");

        //GameModel bidon
        GameModel gameModel = new GameModel();//map bidon


        ArrayList<NPC> NPCs = new ArrayList<NPC>();
        NPCFactory factory = new NPCFactory();
        while(NPCs.size()<=10){
            NPCs.add(factory.getInstance(NPCFactory.Type.STANDARD_NPC,map,gameModel));
            NPCs.add(factory.getInstance(NPCFactory.Type.RAPID_NPC,map, gameModel));
            NPCs.add(factory.getInstance(NPCFactory.Type.GLUE_RESISTANT_NPC,map,gameModel));
            NPCs.add(factory.getInstance(NPCFactory.Type.EXPLOSIVE_RESISTANT_NPC, map, gameModel));
            NPCs.add(factory.getInstance(NPCFactory.Type.SUPER_HEALTH_NPC, map, gameModel));
        }
        for (NPC npc : NPCs){
            npc.run();
        }
    }

    @Test
    public void testTower(){
        //map bidon
        Tile tile = new Tile(1,1,2);
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile);
        Map map = new Map(tiles, 2.0,2.0,1,1,"Map Bidon");

        //GameModel bidon
        GameModel gameModel = new GameModel();//map bidon

        ArrayList<Tower> towers = new ArrayList<Tower>();
        Shop shop = new Shop();

        while(towers.size()<=10){
            towers.add((Tower) shop.getInstance(Shop.Type.STANDARD_TOWER,map));
        }
        for(Tower tower: towers){
            tower.run();
        }


    }

    @Test
    public void testProjectiles(){}
}

