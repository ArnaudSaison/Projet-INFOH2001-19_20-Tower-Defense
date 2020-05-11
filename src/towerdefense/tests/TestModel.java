package towerdefense.tests;

import org.junit.Test;
import towerdefense.Config;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;

import java.io.IOException;

public class TestModel {
    public TestModel() throws IOException {
    }

    @Test
    /**
     * Ce Test vérifie l'instanciation de la classe gameModel.
     */
    public void initializationGameModelTest() throws IOException {
        /*Config config = new Config("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\");
        GameModel gameModel = new GameModel(config, "C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

        Player player = gameModel.getPlayer();
        Wave wave = gameModel.getWave();
        Config config1 = gameModel.getConfig();
        Map map = gameModel.getMap();
        ArrayList<Hittable> hittables = gameModel.getHittables();
        ArrayList<NPC> NPCs = gameModel.getNPCsOnMap();
        ArrayList<Tower> towers = gameModel.getTowers();
        ArrayList<GoldMine> goldMines = gameModel.getGoldMines();

        //On fait des print pour vérifier que tous ce qui doit être instancié dans le jeu apparaît bien:
        System.out.println("Config : " + config1.toString() + "\n" + "=====================");
        System.out.println("Map : " + map.toString() + "\n" + "=====================");
        System.out.println("Player : " + player.toString() + "\n" + "=====================");
        wave.toPrint();

        System.out.println("===========tower============");
        for (Tower tower : towers) {
            System.out.println(tower.toString());
        }
        System.out.println("===========hittables============");
        for (Hittable hittable : hittables) {
            System.out.println(hittable.toString());
        }
        System.out.println("===========goldmine============");
        for (GoldMine goldMine : goldMines) {
            System.out.println(goldMine.toString());
        }*/
    }

    @Test
    /**
     * Ce test vérifie le bon fonctionnement de la classe shop.
     */
    public void shopTest() throws IOException {
        /*Config config = new Config("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\");
        GameModel gameModel = new GameModel(config, "C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

        //Position random donné à la tour (nécessaire pour l'instancier):
        Position p1 = new Position(4.0,4.0,gameModel.getMap());
        Shop shop = gameModel.getShop();
        shop.buyPlaceable(Shop.ShopCases.STANDARD_TOWER,p1);
        //TODO: imprimer la tour achtée.

        //On vérifie que l'argent est bien retirer au joueur:
        System.out.println(gameModel.getPlayer().toString());
    */
    }

    @Test
    /**
     * Ce test vérifie le comportement de projectile: déplacement et dégats causés aux NPC.
     * /!\ ATTENTION : pour éviter un erreur lors de l'éxcution mettre en commentaire l'instruction:
     *                 map.addElementOnMap((Drawable) element);
     *                 de la méthode initializePlaceable, de la classe GameModel.
     */
    public void projectileTest() throws IOException {
        Config config = new Config("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\");
        GameModel gameModel = new GameModel(config, "C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

        //Position de départ:
        Position pInitial = new Position(1.0,1.0,gameModel.getMap());

        //Cible (immobile):
        NPC target = gameModel.getWave().getNextEnemy();
        System.out.println(target.toString());
        System.out.println(target.getPath().toString());

        //Instancie une flèche, notez que le thread des projectiles est démarré dès l'instanciation:
        gameModel.initialize();
        //Arrow arrow = new Arrow(gameModel.getMap(), gameModel, 1, 1,pInitial, target);
        //arrow.run();

        target.initialize();
        target.run();
    }
}