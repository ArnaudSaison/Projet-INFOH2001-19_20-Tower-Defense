package towerdefense.game.model;

import towerdefense.game.Buyable;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.towers.CanonTower;
import towerdefense.game.towers.GlueTower;
import towerdefense.game.towers.StandardTower;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Shop {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Références nécessaires à la création des tours et mines d'or:
    private Map map;
    private GameModel gameModel;
    private Player player;
    public enum ShopCases {STANDARD_TOWER, RAPID_TOWER, LONG_RANGE_TOWER, CANON_TOWER, GLUE_TOWER, GOLDMINE}
    final Properties shopProperties;

    //Buyable liste de spécifications par niveau:
    private ArrayList<ArrayList<Integer>> standardTowerSpe;
    private ArrayList<ArrayList<Integer>> rapidTowerSpe;
    private ArrayList<ArrayList<Integer>> longRangeTowerSpe;
    private ArrayList<ArrayList<Integer>> canonTowerSpe;
    private ArrayList<ArrayList<Integer>> glueTowerSpe;
    private ArrayList<ArrayList<Integer>> goldMineSpe;


    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public Shop(Map map, GameModel gameModel, String shopFilePath) throws IOException {
        this.map = map;
        this.gameModel = gameModel;
        player = gameModel.getPlayer();

        //================================Lecture des propriétés du magasin============================================
        shopProperties = new Properties();
        InputStream shopPropertiesFile = new FileInputStream(shopFilePath+".properties");
        shopProperties.load(shopPropertiesFile);

        //Initialisation des listes de spécification:
        standardTowerSpe = new ArrayList<>();
        rapidTowerSpe = new ArrayList<>();
        longRangeTowerSpe = new ArrayList<>();
        canonTowerSpe = new ArrayList<>();
        glueTowerSpe = new ArrayList<>();
        goldMineSpe = new ArrayList<>();

        //Lectures des spécifications:
        String[] standardLevel1 = shopProperties.getProperty("standard1").split(", ");
        String[] standardLevel2 = shopProperties.getProperty("standard2").split(", ");
        String[] standardLevel3 = shopProperties.getProperty("standard3").split(", ");

        String[] rapidLevel1 = shopProperties.getProperty("rapid1").split(", ");
        String[] rapidLevel2 = shopProperties.getProperty("rapid2").split(", ");
        String[] rapidLevel3 = shopProperties.getProperty("rapid3").split(", ");

        String[] longRangeLevel1 = shopProperties.getProperty("longRange1").split(", ");
        String[] longRangeLevel2 = shopProperties.getProperty("longRange2").split(", ");
        String[] longRangeLevel3 = shopProperties.getProperty("longRange3").split(", ");

        String[] canonLevel1 = shopProperties.getProperty("canon1").split(", ");
        String[] canonLevel2 = shopProperties.getProperty("canon2").split(", ");
        String[] canonLevel3 = shopProperties.getProperty("canon3").split(", ");

        String[] glueLevel1 = shopProperties.getProperty("glue1").split(", ");
        String[] glueLevel2 = shopProperties.getProperty("glue2").split(", ");
        String[] glueLevel3 = shopProperties.getProperty("glue3").split(", ");

        String[] goldMineLevel1 = shopProperties.getProperty("goldMine1").split(", ");
        String[] goldMineLevel2 = shopProperties.getProperty("goldMine2").split(", ");
        String[] goldMineLevel3 = shopProperties.getProperty("goldMine3").split(", ");


        //Traitement sur les listes de spécification:
        standardTowerSpe.add(convertToIntegerList(standardLevel1));
        standardTowerSpe.add(convertToIntegerList(standardLevel2));
        standardTowerSpe.add(convertToIntegerList(standardLevel3));

        rapidTowerSpe.add(convertToIntegerList(rapidLevel1));
        rapidTowerSpe.add(convertToIntegerList(rapidLevel2));
        rapidTowerSpe.add(convertToIntegerList(rapidLevel3));

        longRangeTowerSpe.add(convertToIntegerList(longRangeLevel1));
        longRangeTowerSpe.add(convertToIntegerList(longRangeLevel2));
        longRangeTowerSpe.add(convertToIntegerList(longRangeLevel3));

        canonTowerSpe.add(convertToIntegerList(canonLevel1));
        canonTowerSpe.add(convertToIntegerList(canonLevel2));
        canonTowerSpe.add(convertToIntegerList(canonLevel3));

        glueTowerSpe.add(convertToIntegerList(glueLevel1));
        glueTowerSpe.add(convertToIntegerList(glueLevel2));
        glueTowerSpe.add(convertToIntegerList(glueLevel3));

        goldMineSpe.add(convertToIntegerList(goldMineLevel1));
        goldMineSpe.add(convertToIntegerList(goldMineLevel2));
        goldMineSpe.add(convertToIntegerList(goldMineLevel3));
    }

    /*==================================================================================================================
                                                   GESTION DES CAS
    ==================================================================================================================*/
    public Buyable getInstance(ShopCases type){
        //Spécification des attributs et de base des tours:
        String[] standardLevel1 = shopProperties.getProperty("standard1").split(", ");

        //===============================================Gestion des cas================================================
        Buyable res = null;
        switch (type) {
            //Tours:
            case STANDARD_TOWER: res = new StandardTower(map, gameModel, standardTowerSpe,"StandardTower");
            break;
            case RAPID_TOWER: res = new StandardTower(map, gameModel, rapidTowerSpe,"RapidTower");
            break;
            case LONG_RANGE_TOWER: res = new StandardTower(map, gameModel, longRangeTowerSpe,"LongRangeTower");
            break;
            case CANON_TOWER: res = new CanonTower(map, gameModel, canonTowerSpe);
            break;
            case GLUE_TOWER: res = new GlueTower(map, gameModel, glueTowerSpe);
            break;

            //Mine d'or:
            case GOLDMINE: res = new GoldMine(map, gameModel, goldMineSpe);
            break;
        }
        return res;
    }


    /**
     * Permet de convertir une liste de string en une ArrayList d'entier.
     * @param list de string à convertir en liste d'entier.
     * @return
     */
    private ArrayList<Integer> convertToIntegerList(String [] list){
        ArrayList<Integer> res = new ArrayList<>();
        for (String elem : list){
            res.add(Integer.parseInt(elem));
        }
        return res;
    }
}