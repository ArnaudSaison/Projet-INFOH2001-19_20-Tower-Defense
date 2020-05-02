package towerdefense.game.model;

import towerdefense.game.map.Map;

import java.util.ArrayList;

public class Shop {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Références nécessaires à la création des tours et mines d'or:
    private Map map;
    private GameModel gameModel;

    private ArrayList<String> standardNPCTypes;
    private ArrayList<String> specialNPCTypes;

    //Paramètres de configuration lus dans le fichier wave.properties:
    private int standardRange;
    private int standardFireRate;
    private int standardDamageDeal;
    private int standardRangeIncrement;
    private int standardFireRateIncrement;
    private int standardDamageDealIncrement;
    private int specialRange;
    private int specialFireRate;
    private int specialDamageDeal;
    private int specialRangeIncrement;
    private int specialFireRateIncrement;
    private int specialDamageDealIncrement;
}
