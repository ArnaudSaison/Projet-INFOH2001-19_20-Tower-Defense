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

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public Shop(Map map, GameModel gameModel, String shopFilePath) throws IOException {
        this.map = map;
        this.gameModel = gameModel;
        player = gameModel.getPlayer();

        //================================Lecture des propriétés du magasin============================================
        shopProperties = new Properties();
        InputStream shopPropertiesFile = new FileInputStream(shopFilePath);
        shopProperties.load(shopPropertiesFile);
    }

    /*==================================================================================================================
                                                   GESTION DES CAS
    ==================================================================================================================*/
    public Buyable getInstance(ShopCases type){
        //Spécification des attributs et de base des tours:
        int standardRange = Integer.parseInt(shopProperties.getProperty("standardRange"));
        int standardFireRate = Integer.parseInt(shopProperties.getProperty("standardFireRate"));
        int standardDamageDeal = Integer.parseInt(shopProperties.getProperty("standardDamageDeal"));
        int standardMaxEnemyNumber = Integer.parseInt(shopProperties.getProperty("standardMaxEnemyNumber"));

        int standardRangeIncrement = Integer.parseInt(shopProperties.getProperty("standardRangeIncrement"));
        int standardFireRateIncrement = Integer.parseInt(shopProperties.getProperty("standardFireRateIncrement"));

        int specialRange = Integer.parseInt(shopProperties.getProperty("specialRange"));
        int specialFireRate = Integer.parseInt(shopProperties.getProperty("specialFireRate"));
        int specialDamageDeal = Integer.parseInt(shopProperties.getProperty("specialDamageDeal"));
        int specialMaxEnemyNumber = Integer.parseInt(shopProperties.getProperty("specialMaxEnemyNumber"));

        //Spécification des attributs et de base des mines d'or:
        int maxLevel = Integer.parseInt(shopProperties.getProperty("maxLevel"));
        int price = Integer.parseInt(shopProperties.getProperty("price"));
        int productionRate = Integer.parseInt(shopProperties.getProperty("productionRate"));
        int maxGoldStorage = Integer.parseInt(shopProperties.getProperty("maxGoldStorage"));

        int priceIncrement = Integer.parseInt(shopProperties.getProperty("priceIncrement"));
        int productionRateIncrement = Integer.parseInt(shopProperties.getProperty("productionRateIncrement"));
        int maxGoldStorageIncrement = Integer.parseInt(shopProperties.getProperty("maxGoldStorageIncrement"));

        //Prix:
        int standardPrice = Integer.parseInt(shopProperties.getProperty("standardPrice"));
        int rapidPrice = Integer.parseInt(shopProperties.getProperty("rapidPrice"));
        int longRangePrice = Integer.parseInt(shopProperties.getProperty("longRangePrice"));
        int canonPrice = Integer.parseInt(shopProperties.getProperty("canonPrice"));
        int gluePrice = Integer.parseInt(shopProperties.getProperty("gluePrice"));

        int goldMinePrice = Integer.parseInt(shopProperties.getProperty("goldMinePrice"));

        //===============================================Gestion des cas================================================
        Buyable res = null;
        switch (type) {
            //Tours:
            case STANDARD_TOWER: res = new StandardTower(map, gameModel,standardPrice, standardRange, standardFireRate, standardDamageDeal, standardMaxEnemyNumber, "StandardTower");
                player.decreaseGold(standardPrice);
            break;
            case RAPID_TOWER: res = new StandardTower(map, gameModel,rapidPrice, standardRange, standardFireRate+ standardFireRateIncrement, standardDamageDeal, standardMaxEnemyNumber, "RapidTower");
                player.decreaseGold(rapidPrice);
            break;
            case LONG_RANGE_TOWER: res = new StandardTower(map, gameModel,longRangePrice, standardRange +standardRangeIncrement, standardFireRate, standardDamageDeal, standardMaxEnemyNumber, "LongRangeTower");
                player.decreaseGold(longRangePrice);
            break;
            case CANON_TOWER: res = new CanonTower(map, gameModel,canonPrice, specialRange, specialFireRate, specialDamageDeal, specialMaxEnemyNumber);
                player.decreaseGold(canonPrice);
            break;
            case GLUE_TOWER: res = new GlueTower(map, gameModel,gluePrice, specialRange, specialFireRate, specialDamageDeal, specialMaxEnemyNumber);
                player.decreaseGold(gluePrice);
            break;

            //Mine d'or:
            case GOLDMINE: res = new GoldMine(map, maxLevel, goldMinePrice, priceIncrement, productionRate, productionRateIncrement, maxGoldStorage, maxGoldStorageIncrement );
                player.decreaseGold(goldMinePrice);
            break;
        }
        return res;
    }
}