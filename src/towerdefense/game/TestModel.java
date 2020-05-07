package towerdefense.game;

import org.junit.Test;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Player;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.NPC;
import towerdefense.game.towers.StandardTower;
import towerdefense.game.waves.Wave;

import java.io.IOException;

public class TestModel {


    @Test
    /**Vérifie que tout est bien instancié par la classe gameModel*/
    public void testGameModel() throws IOException {
        GameModel gameModel = new GameModel();

        System.out.println("======================PLAYER ET PREMIERE VAGUE============================");

        Player player = gameModel.getPlayer();
        System.out.println("=======================" + player.toString() + "============================");
        Wave wave = gameModel.getWave();
        //wave.toPrint();


        //System.out.println("======================TOURS ET MINES D'OR============================");
        Shop shop = gameModel.getShop();
        StandardTower standardTower = (StandardTower) shop.getInstance(Shop.ShopCases.STANDARD_TOWER);
        GoldMine goldMine = (GoldMine) shop.getInstance(Shop.ShopCases.GOLDMINE);

        //===================================Contrôle passage de niveau=================================================

        /*System.out.println("===========Niveau1============");
        System.out.println(standardTower.toString());
        System.out.println("=======================");
        System.out.println(goldMine.toString());

        standardTower.levelUp();
        goldMine.levelUp();

        System.out.println("===========Niveau2============");
        System.out.println(standardTower.toString());
        System.out.println("=======================");
        System.out.println(goldMine.toString());

        standardTower.levelUp();
        goldMine.levelUp();

        System.out.println("===========Niveau3============");
        System.out.println(standardTower.toString());
        System.out.println("=======================");
        System.out.println(goldMine.toString());

        standardTower.levelUp();
        goldMine.levelUp();

        System.out.println("===========Ne dois pas passer de niveau============");
        System.out.println(standardTower.toString());
        System.out.println("=======================");
        System.out.println(goldMine.toString());
        */

        /*ArrayList<Hittable> hittables = gameModel.getHittables();
        ArrayList<Tower> towers= gameModel.getTowers();
        ArrayList<GoldMine> goldMines = gameModel.getGoldMines();
        System.out.println("===========tower============");
        for(Tower tower : towers){
            System.out.println(tower.toString());
        }
        System.out.println("===========hittables============");
        for(Hittable hittable : hittables){
            System.out.println(hittable.toString());
        }
        System.out.println("===========goldmine============");
        for(GoldMine goldMine: goldMines){
            System.out.println(goldMine.toString());
        }*/

        //==================================Contrôle des threads========================================================
        gameModel.initialize();
        //gameModel.run(); //TODO: contrôle total des pourcentage = 1
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.getMap("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");
        Position p = new Position(4,5,map);
        standardTower.setPosition(p);
        NPC npc = wave.getNextEnemy();
        gameModel.initializeNPC(npc);
        standardTower.initialize();

        System.out.println (npc.toString());

        standardTower.run();
    }
}