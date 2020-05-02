package towerdefense.game.model;

import towerdefense.game.Buyable;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.towers.CanonTower;
import towerdefense.game.towers.GlueTower;
import towerdefense.game.towers.StandardTower;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public Shop(Map map, GameModel gameModel, String waveFilePath) {
        this.map = map;
        this.gameModel = gameModel;
    }

    /*==================================================================================================================
                                                   GESTION DES CAS
    ==================================================================================================================*/
    public Buyable getInstance(String type, String shopFilePath) throws FileNotFoundException {
        //================================Lecture des propriétés du magasin============================================

        //Chemin d'accès au fichier shop.properties:
        final Properties shopProperties = new Properties();
        InputStream shopPropertiesFile = new FileInputStream(shopFilePath + "/wave.properties");
        Properties.load(shopPropertiesFile); //TODO : Pourquoi ?

        //Spécification des attributs et de base des tours:
        int standardRange = Integer.parseInt(shopProperties.getProperty("standardRange"));
        int standardFireRate = Integer.parseInt(shopProperties.getProperty("standardFireRate"));
        int standardDamageDeal = Integer.parseInt(shopProperties.getProperty("standardDamageDeal"));
        int standardMaxEnemyNumber = Integer.parseInt(shopProperties.getProperty("standardMaxEnemyNumber"));

        int standardRangeIncrement = Integer.parseInt(shopProperties.getProperty("standardRangeIncrement"));
        int standardFireRateIncrement = Integer.parseInt(shopProperties.getProperty("standardFireRateIncrement"));
        int standardDamageDealIncrement = Integer.parseInt(shopProperties.getProperty("standardDamageDealIncrement"));

        int specialRange = Integer.parseInt(shopProperties.getProperty("specialRange"));
        int specialFireRate = Integer.parseInt(shopProperties.getProperty("specialFireRate"));
        int specialDamageDeal = Integer.parseInt(shopProperties.getProperty("specialDamageDeal"));
        int specialMaxEnemyNumber = Integer.parseInt(shopProperties.getProperty("specialMaxEnemyNumber"));

        int specialRangeIncrement = Integer.parseInt(shopProperties.getProperty("specialRangeIncrement"));
        int specialFireRateIncrement = Integer.parseInt(shopProperties.getProperty("specialFireRateIncrement"));
        int specialDamageDealIncrement = Integer.parseInt(shopProperties.getProperty("specialDamageDealIncrement"));

        //Spécification des attributs et de base des mines d'or:
        int maxLevel = Integer.parseInt(shopProperties.getProperty("maxLevel"));
        int price = Integer.parseInt(shopProperties.getProperty("price"));
        int productionRate = Integer.parseInt(shopProperties.getProperty("productionRate"));
        int maxGoldStorage = Integer.parseInt(shopProperties.getProperty("maxGoldStorage"));

        int priceIncrement = Integer.parseInt(shopProperties.getProperty("priceIncrement"));
        int productionRateIncrement = Integer.parseInt(shopProperties.getProperty("productionRateIncrement"));
        int maxGoldStorageIncrement = Integer.parseInt(shopProperties.getProperty("maxGoldStorageIncrement"));

        //===============================================Gestion des cas================================================
        Buyable res = null;
        switch (type.toLowerCase()) {
            //Tours:
            case "standardtower": res = new StandardTower(map, gameModel, standardRange, standardFireRate, standardDamageDeal, standardMaxEnemyNumber, type);
            break;
            case "rapidtower": res = new StandardTower(map, gameModel, standardRange, standardFireRate, standardDamageDeal, standardMaxEnemyNumber, type);
            break;
            case "longrangetower": res = new StandardTower(map, gameModel, standardRange, standardFireRate, standardDamageDeal, standardMaxEnemyNumber, type);
            break;
            case "canontower": res = new CanonTower(map, gameModel, specialRange, specialFireRate, specialDamageDeal, specialMaxEnemyNumber);
            break;
            case "gluetower": res = new GlueTower(map, gameModel, specialRange, specialFireRate, specialDamageDeal, specialMaxEnemyNumber);
            break;

            //Mine d'or:
            case "goldmine": res = new GoldMine(map, maxLevel, price, priceIncrement, productionRate, productionRateIncrement, maxGoldStorage, maxGoldStorageIncrement );
            break;
        }
        return res;
    }
}