package towerdefense.game;

import org.junit.Test;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Player;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.NPC;
import towerdefense.game.waves.Wave;
import towerdefense.game.waves.WaveFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class TestGameModel {
    //@Test
    /**Vérifie via un print que les vagues sont composées selon les indications fournies par les fichier properties.*/
    public void testGameModel() throws IOException {
        GameModel gameModelTest = new GameModel();
        //gameModelTest.initialize();
        //gameModelTest.run();

        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.getMap("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

        Wave vague = gameModelTest.donneVague();
        NPC npc = vague.getNextEnemy();
        gameModelTest.initializeNPC(npc);
        System.out.println(gameModelTest.getNPCsOnMap());
        //npc.setIsArrived(true);
        gameModelTest.killNPC(npc);
        System.out.println(gameModelTest.getNPCsOnMap());
        Player player = gameModelTest.getPlayer();
        System.out.println(player.getHealth());
        System.out.println(player.getGold());

        Shop shop = new Shop(map, gameModelTest,"C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\shops\\shop.properties");
        shop.getInstance(Shop.ShopCases.STANDARD_TOWER);
    }
    //@Test
    public void testWaveFactory() throws IOException {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.getMap("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");
        GameModel gameModelTest = new GameModel();

        WaveFactory waveFactory = new WaveFactory(map,gameModelTest,"C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");
        Wave wave = waveFactory.getWave("easy",0,0);

        waveFactory.afficheFichiersSpec();
    }

    //=======================================test getAllMapSpecificationsFiles==========================================
    ArrayList<File> res = new ArrayList<>();

    public void getAllMapSpecificationsFiles(File mapFolder){
        for (File fileEntry : mapFolder.listFiles()) {
            if (fileEntry.isDirectory()) {
                getAllMapSpecificationsFiles(fileEntry);
            } else {
                res.add(fileEntry);
            }
        }
    }

    String mapsFolderPath = "C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1";
    File mapFolder = new File(mapsFolderPath);

    //@Test
    public void testGetAllMapsSpec(){
        getAllMapSpecificationsFiles(mapFolder);
        for (File file : res) {
            System.out.println(file.getName());
        }
    }

    //==================================================================================================================

    @Test
    public void testShop() throws IOException {
        Properties shopProperties = new Properties();
        InputStream shopPropertiesFile = new FileInputStream("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\shops\\shop");
        shopProperties.load(shopPropertiesFile);
        try{
            String[] standardLevel1 = shopProperties.getProperty("standard1").split(", ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}