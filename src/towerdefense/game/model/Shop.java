package towerdefense.game.model;

import towerdefense.game.Buyable;
import towerdefense.game.Placeable;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
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

    public enum ShopCases {STANDARD_TOWER, RAPID_TOWER, LONG_RANGE_TOWER, CANON_TOWER, GLUE_TOWER, GOLD_MINE}

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
        this.player = gameModel.getPlayer();

        //================================Lecture des propriétés du magasin============================================
        shopProperties = new Properties();
        InputStream shopPropertiesFile = new FileInputStream(shopFilePath);
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

    /**
     * Permet d'acheter et instancier une tour ou une mine d'or à placer sur la carte
     *
     * @param type type d'objet qu'on veut acheter
     * @param pos  position à laquelle on souhaite le mettre.
     *             Le shop ne s'occupe que de vendre les objet, et ne vérifie donc pas qu'il soit placé au bon endroit
     * @return true si la transaction a pu avoir lieu
     */
    public boolean buyPlaceable(ShopCases type, Position pos, Map map) {
        boolean canBeBought = map.canBeBuiltOn(pos.getTileX(), pos.getTileY(), getGraphicsProportion(type)); // vérification qu'on puisse construire l'élément à cet endroit

        if (canBeBought) {
            //===============================================Gestion des cas================================================
            int price = getItemProp(type, ItemProp.PRICE, 1); // récupération du prix
            canBeBought = player.getGold() >= price;

            if (canBeBought) { // vérification que le joueur a assez d'argent
                player.decreaseGold(price);

                Buyable bought = null; // initialisation
                switch (type) { // Récupération de ce qui a été acheté
                    //Tours:
                    case STANDARD_TOWER:
                        bought = new StandardTower(map, pos, gameModel, standardTowerSpe, "StandardTower");
                        break;
                    case RAPID_TOWER:
                        bought = new StandardTower(map, pos, gameModel, rapidTowerSpe, "RapidTower");
                        break;
                    case LONG_RANGE_TOWER:
                        bought = new StandardTower(map, pos, gameModel, longRangeTowerSpe, "LongRangeTower");
                        break;
                    case CANON_TOWER:
                        bought = new CanonTower(map, pos, gameModel, canonTowerSpe);
                        break;
                    case GLUE_TOWER:
                        bought = new GlueTower(map, pos, gameModel, glueTowerSpe);
                        break;

                    //Mine d'or:
                    case GOLD_MINE:
                        bought = new GoldMine(map, pos, gameModel, goldMineSpe);
                        break;
                }

                // Ajout de l'élément acheté au modèle
                gameModel.initializePlaceable((Placeable) bought); // Ce cast n'est pas dangereux car cette méthode est exclusivement réservée à l'instanciation de placeable
            } else {
            }
        } else {
        }

        return canBeBought;
    }

    // ==================== Getters ====================
    public enum ItemProp {PRICE, RANGE, RATE, DAMAGE, MAX_TARGETS, PROD_RATE, MAX_STORAGE}

    public static int getPropID(ItemProp prop) {
        int res = 0;

        switch (prop) {
            case PRICE:
                res = 0;
                break;
            case RANGE:
            case PROD_RATE:
                res = 1;
                break;
            case DAMAGE:
            case MAX_STORAGE:
                res = 2;
                break;
            case RATE:
                res = 3;
                break;
            case MAX_TARGETS:
                res = 4;
                break;
        }
        return res;
    }

    public static String getPropName(ShopCases item, ItemProp prop) {
        String res = null;

        switch (prop) {
            case PRICE:
                res = "Price";
                break;
            case RANGE:
                res = "Range";
                break;
            case DAMAGE:
                if (item == ShopCases.GLUE_TOWER) {
                    res = "Glued time";
                } else {
                    res = "Damage";
                }
                break;
            case RATE:
                res = "Fire rate";
                break;
            case MAX_TARGETS:
                res = "Max targets";
                break;
            case PROD_RATE:
                res = "Production rate";
                break;
            case MAX_STORAGE:
                res = "Max gold storage";
                break;
        }
        return res;
    }

    public int getItemProp(ShopCases item, ItemProp prop, int level) {
        int res = 0;
        level -= 1;

        switch (item) {
            //Tours:
            case STANDARD_TOWER:
                res = standardTowerSpe.get(level).get(getPropID(prop));
                break;
            case RAPID_TOWER:
                res = rapidTowerSpe.get(level).get(getPropID(prop));
                break;
            case LONG_RANGE_TOWER:
                res = longRangeTowerSpe.get(level).get(getPropID(prop));
                break;
            case CANON_TOWER:
                res = canonTowerSpe.get(level).get(getPropID(prop));
                break;
            case GLUE_TOWER:
                res = glueTowerSpe.get(level).get(getPropID(prop));
                break;

            //Mine d'or:
            case GOLD_MINE:
                res = goldMineSpe.get(level).get(getPropID(prop));
                break;
        }
        return res;
    }

    public static ArrayList<ItemProp> getPropertiesOfItem(ShopCases item) {
        ArrayList<ItemProp> res = new ArrayList<>();

        switch (item) {
            //Tours:
            case STANDARD_TOWER:
            case RAPID_TOWER:
            case LONG_RANGE_TOWER:
            case CANON_TOWER:
            case GLUE_TOWER:
                res.add(ItemProp.PRICE);
                res.add(ItemProp.RANGE);
                res.add(ItemProp.RATE);
                res.add(ItemProp.DAMAGE);
                res.add(ItemProp.MAX_TARGETS);
                break;
            //Mine d'or:
            case GOLD_MINE:
                res.add(ItemProp.PRICE);
                res.add(ItemProp.PROD_RATE);
                res.add(ItemProp.MAX_STORAGE);
                break;
        }
        return res;
    }

    public static String getIconPath(ShopCases item) {
        String res = null;

        switch (item) {
            //Tours:
            case STANDARD_TOWER:
            case RAPID_TOWER:
            case LONG_RANGE_TOWER:
                res = "towers/archer_tower/tower.png";
                break;
            case CANON_TOWER:
                res = "towers/cannon_tower/tower.png";
                break;
            case GLUE_TOWER:
                res = "towers/glue_tower/tower.png";
                break;

            //Mine d'or:
            case GOLD_MINE:
                res = "gold_mine/gold_mine.png";
                break;
        }
        return res;
    }

    public static int getGraphicsProportion(ShopCases item) {
        int res = 2;

        switch (item) {
            case STANDARD_TOWER:
            case RAPID_TOWER:
            case LONG_RANGE_TOWER:
            case CANON_TOWER:
            case GOLD_MINE:
                res = 2;
                break;
            case GLUE_TOWER:
                res = 1;
                break;
        }
        return res;
    }

    public static String getItemName(ShopCases item) {
        String res = null;

        switch (item) {
            //Tours:
            case STANDARD_TOWER:
                res = "Archer Tower";
                break;
            case RAPID_TOWER:
                res = "Rapid Tower";
                break;
            case LONG_RANGE_TOWER:
                res = "Long Range Tower";
                break;
            case CANON_TOWER:
                res = "Cannon Tower";
                break;
            case GLUE_TOWER:
                res = "Glue Tower";
                break;

            //Mine d'or:
            case GOLD_MINE:
                res = "Gold Mine";
                break;
        }
        return res;
    }

    public static ArrayList<ShopCases> getBuyableItems() {
        ArrayList<ShopCases> res = new ArrayList<>();
        res.add(ShopCases.STANDARD_TOWER);
        res.add(ShopCases.RAPID_TOWER);
        res.add(ShopCases.LONG_RANGE_TOWER);
        res.add(ShopCases.CANON_TOWER);
        res.add(ShopCases.GLUE_TOWER);
        res.add(ShopCases.GOLD_MINE);
        return res;
    }

    /**
     * Permet de convertir une liste de string en une ArrayList d'entier.
     *
     * @param list de string à convertir en liste d'entier.
     * @return
     */
    private ArrayList<Integer> convertToIntegerList(String[] list) {
        ArrayList<Integer> res = new ArrayList<>();
        for (String elem : list) {
            res.add(Integer.parseInt(elem));
        }
        return res;
    }
}