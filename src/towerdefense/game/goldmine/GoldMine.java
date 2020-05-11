package towerdefense.game.goldmine;

import towerdefense.game.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Shop;
import towerdefense.view.Printable;
import towerdefense.view.goldmines.GoldMineView;

import java.util.ArrayList;

public class GoldMine implements ProducesGold, Buyable, Upgradable, Placeable, Drawable, Runnable {
    private static final Shop.ShopCases ID = Shop.ShopCases.GOLD_MINE;
    private static final Object syncKeyA = new Object();

    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Attributs relatifs au passage de niveau:
    private int level;
    private int maxLevel;

    //Attributs de specification:
    private ArrayList<ArrayList<Integer>> goldMineSpe;
    private int price; //prix que le joueur doit payer pour acheter/améliorer la mine d'or.
    private int productionRate; //quantité d'or produit en une minute.
    private int goldStorage; //quantité d'or stockée par la mine d'or, nécessite une action du joueur pour être récupérée.
    private int maxGoldStorage; //quantité d'or maximale que la mine d'or peut stocker.

    //Autres:
    private Map map;
    private Position position;
    private GameModel gameModel;
    private Thread tGoldMine;
    private int size; // en largeur de cases
    protected String graphicsName;

    // javaFX
    private GoldMineView goldMineView;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/

    /**
     * Constructeur.
     *
     * @param goldMineSpe = [[level1Spec], ..., [levelNSpec]]
     */
    public GoldMine(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> goldMineSpe) {
        this.gameModel = gameModel;
        this.map = map;

        //Initialisation du niveau:
        this.goldMineSpe = goldMineSpe;
        level = 1;
        maxLevel = 3;

        //Initialisation des attributs:
        ArrayList<Integer> level1Spe = goldMineSpe.get(0);
        setAttributes(level1Spe);

        //Initialisation de la position et du thread:
        position = pos;
        tGoldMine = new Thread(this);

        this.graphicsName = Shop.getIconPath(ID);
        size = Shop.getGraphicsProportion(ID); // récupération de la taille
        /* la position d'une mine d'or est définie par la position de son centre
        Cependant, pour la vue, il est plus pratique de définir le coin surpérieur gauche */
        this.position = pos.getAdded(new Position(size / 2.0 * map.getTileMetricWidth(), size / 2.0 * map.getTileMetricWidth(), map));
        // Bloquage des cases (une tour fait toujours 2 cases de largeur
        blockTiles();
    }

    /**
     * Sert à bloquer les cases nouvellement occupées
     */
    protected void blockTiles() {
        for (int i = 0; i < size; i++) { // largeur
            for (int j = 0; j < size; j++) { // hauteur
                map.getTile(getCornerPosition().getTileX() + i, getCornerPosition().getTileY() + j).setBlockedState(true);
            }
        }
    }

    /*==================================================================================================================
                                                   PRODUCTION DE L'OR
    ==================================================================================================================*/

    /**
     * Permet de produire de l'or qui sera ensuite stocké
     */
    @Override
    public void produceGold(int increment) {
        synchronized (syncKeyA) {
            if (goldStorage + increment < maxGoldStorage) {
                goldStorage += increment;
            } else {
                goldStorage = maxGoldStorage;
            }
        }
    }

    /**
     * Permet de récupérer l'or stocké
     */
    @Override
    public void retrieveGold() {
        synchronized (syncKeyA) {
            gameModel.getPlayer().increaseGold(goldStorage);
            goldStorage = 0;
        }
    }

    /*==================================================================================================================
                                                   PASSAGE DE NIVEAU
    ==================================================================================================================*/
    @Override
    public boolean canBeLeveledUp() {
        return level < maxLevel;
    }

    /**
     * Si le niveau maximal n'est pas atteint, passe les attributs à leur valeur spécifiée par la liste levelSpe (cf: setAttributes())
     */
    @Override
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            ArrayList<Integer> levelSpe = goldMineSpe.get(level - 1);
            setAttributes(levelSpe);
        }
    }

    /**
     * Passe tous les attributs à leur valeur spécifiée par la liste prise en argument.
     *
     * @param levelSpe = [int price, int productionRate, int maxGoldStorage].
     */
    private void setAttributes(ArrayList<Integer> levelSpe) {
        price = levelSpe.get(0);
        productionRate = levelSpe.get(1);
        maxGoldStorage = levelSpe.get(2);
    }

    /*==================================================================================================================
                                                   GESTION DU THREAD
    ==================================================================================================================*/

    /**
     * Démarre le thread de la mine d'or
     */
    @Override
    public void initialize() {
        if (gameModel.isRunning()) {
            tGoldMine.start();
        }
    }

    /**
     * Définition du thread de la mine d'or: si le jeu n'est pas en pause alors produit de l'or à la cadence imposée par
     * l'attribut productionRate, sinon place le thread en pause
     */
    @Override
    public void run() {
        long timer = 0;
        double sleepTime = 1.0 / gameModel.getConfig().getModelFrameRate() * 1000;
        int productionTime = (int) Math.round(1.0 / sleepTime * 1000); // la mine produira toujours toutes les secondes pour éviter d'avoir un temps = 0

        try {
            while (gameModel.isRunning()) { // si le jeu est en cours
                if (!gameModel.getPaused()) { // si le jeu n'est pas en pause
                    if (timer == 0) { // si le timer est temriné
                        produceGold(productionRate); // produire l'or que la mine produit en une seconde
                        timer = productionTime;
                    } else {
                        timer--; // évolution du timer
                    }
                }

                Thread.sleep((long) sleepTime);
            }
        } catch (InterruptedException exception) { // gestion des possibles erreurs lors de l'exécution du thread
            exception.printStackTrace();
        }
    }

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/

    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    @Override
    public void initDrawing() {
        goldMineView = new GoldMineView(this, map, graphicsName, size, gameModel.getShop());
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    @Override
    public void updateDrawing() {
        goldMineView.update();
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    @Override
    public Printable getDrawing() {
        return goldMineView;
    }

    /*==================================================================================================================
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
    @Override
    public int getCost() {
        return price;
    }

    /**
     * Renvoie la position du centre de la mine
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Renvoie la position du coin supérieur gauche de la mine
     */
    public Position getCornerPosition() {
        return position.getAdded(new Position(-size / 2.0 * map.getTileMetricWidth(), -size / 2.0 * map.getTileMetricWidth(), map));
    }

    /**
     * Changer la position de la mine
     *
     * @param position
     */
    @Override
    public void setPosition(Position position) {
        this.position = position.getAdded(new Position(size / 2.0 * map.getTileMetricWidth(), size / 2.0 * map.getTileMetricWidth(), map));
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public Shop.ShopCases getID() {
        return ID;
    }

    public int getGoldStorage() {
        synchronized (syncKeyA) {
            return goldStorage;
        }
    }

    public int getMaxGoldStorage() {
        return maxGoldStorage;
    }

    /*==================================================================================================================
                                                   AUTRES
    ==================================================================================================================*/
    @Override
    public String toString() {
        return "Mine d'or :\n - position: " + position + "\n" +
                "- level: " + level + "\n" +
                "- maxLevel: " + maxLevel + "\n" +
                "- maxGoldStorage: " + maxGoldStorage + "\n" +
                "- goldStorage: " + goldStorage + "\n" +
                "- productionRate: " + productionRate + "\n" +
                "- price: " + price + ".";
    }
}