package towerdefense.game;

import org.junit.Test;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.model.GameModel;
import towerdefense.game.waves.Wave;
import towerdefense.game.waves.WaveFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestGameModel {
    @Test
    /**Vérifie via un print que les vagues sont composées selon les indications fournies par les fichier properties.*/
    public void testGameModel() throws IOException {
        GameModel gameModelTest = new GameModel();
        gameModelTest.initialize();
        gameModelTest.run();
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

}