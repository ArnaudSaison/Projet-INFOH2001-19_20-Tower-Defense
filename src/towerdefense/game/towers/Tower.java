package towerdefense.game.towers;

import towerdefense.game.Buyable;
import towerdefense.game.Drawable;
import towerdefense.game.Placeable;
import towerdefense.game.Upgradable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.NPC;
import towerdefense.view.Printable;
import towerdefense.view.towers.TowerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable, Runnable {
    private static final Object syncKeytargetAquisition = new Object();

    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Attributs relatifs au passage de niveau:
    protected Map map;
    protected int level;
    static int maxLevel;
    protected int price;

    //Attributs de specification:
    ArrayList<ArrayList<Integer>> towerSpe;
    protected double range;
    protected int fireRate; //en coups par seconde.
    protected int damageDeal;
    protected ArrayList<NPC> targets;
    protected int maxTargetNumber;

    //Autres
    protected int size; // en largeur de cases
    protected Position position;
    protected GameModel gameModel;
    protected Thread tTower;

    // JavaFX
    private TowerView towerView;
    protected String graphicsName;

    //TODO: gestion du prix via un fichier properties ?
    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/

    /**
     * Constructeur
     *
     * @param towerSpe [[level1Spec], ..., [levelNSpec]]
     */
    public Tower(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe, Shop.ShopCases ID) {
        this.gameModel = gameModel;
        this.map = map;

        //Initialisation du niveau:
        this.towerSpe = towerSpe;
        level = 1;
        maxLevel = 3;

        //Initialisation des attributs:
        ArrayList<Integer> level1Spe = towerSpe.get(0);
        setAttributes(level1Spe);
        targets = new ArrayList<>();

        size = Shop.getGraphicsProportion(ID); // récupération de la taille
        /* la position d'une tour est définie par la position de son centre
        Cependant, pour la vue, il est plus pratique de définir le coin surpérieur gauche */
        this.position = pos.getAdded(new Position(size / 2.0 * map.getTileMetricWidth(), size / 2.0 * map.getTileMetricWidth(), map));
        // Bloquage des cases (une tour fait toujours 2 cases de largeur
        blockTiles();

        //Initialisation du thread:
        tTower = new Thread(this);
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
                                                TRAITEMENT DES CIBLES
    ==================================================================================================================*/

    /**
     * Test si les ennemis sur la carte sont à portée de tir.
     */
    public void targetAcquisition() {
        synchronized (syncKeytargetAquisition) {
            ArrayList<NPC> npcsOnMap = gameModel.getNPCsOnMapClone();
            Collections.shuffle(npcsOnMap);
            int i = 0;
            while (!npcsOnMap.isEmpty() && targets.size() < maxTargetNumber && i < npcsOnMap.size()) {
                NPC npc = npcsOnMap.get(i);
                if ((npc.getPosition()).getDistance(position) <= range)
                    targets.add(npc);
                i++;
            }
        }
    }

    public void attack() {
        targetAcquisition();
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
            ArrayList<Integer> levelSpe = towerSpe.get(level - 1);
            setAttributes(levelSpe);
        }
    }

    /**
     * Passe tous les attributs à leur valeur spécifiée par la liste prise en argument.
     *
     * @param levelSpe [int price, int range, int fireRate, int damageDeal, maxEnemyNumber]
     */
    private void setAttributes(ArrayList<Integer> levelSpe) {
        price = levelSpe.get(0);
        range = levelSpe.get(1);
        fireRate = levelSpe.get(2);
        damageDeal = levelSpe.get(3);
        maxTargetNumber = levelSpe.get(4);
    }

    /*==================================================================================================================
                                                     GESTION DU THREAD
    ==================================================================================================================*/

    /**
     * Démarre le thread de la tour
     */
    @Override
    public void initialize() {
        if (gameModel.getRunning()) {
            tTower.start();
        }
    }

    /**
     * Si le jeu tourne, vérifie sa distance aux NPCs sur la carte, si à porté alors attaque
     */
    @Override
    public void run() {
        long timer = 0;
        double sleepTime = 1.0 / gameModel.getConfig().getModelFrameRate() * 1000; // temps d'une itération (en ms)
        int reloadTime = (int) Math.round(1.0 / fireRate / sleepTime * 1000);

        try {
            while (gameModel.getRunning()) { // si le jeu est en cours
                if (!gameModel.getPaused()) { // si le jeu n'est pas en pause
                    if (timer == 0) { // si le timer est temriné
                        attack(); // lancer une attaque
                        targets.clear();
                        timer = reloadTime; // réglage du temps de recharge
                    } else {
                        timer--;
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
        towerView = new TowerView(this, map, graphicsName, size, gameModel.getShop());
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    @Override
    public void updateDrawing() {
        towerView.update();
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    @Override
    public Printable getDrawing() {
        return towerView;
    }

    /*==================================================================================================================
                                                    AUTRES
    ==================================================================================================================*/
    @Override
    public String toString() {
        return "Tour :\n - position: " + position + "\n" +
                "- level: " + level + "\n" +
                "- maxLevel: " + maxLevel + "\n" +
                "- range: " + range + "\n" +
                "- fireRate: " + fireRate + "\n" +
                "- damageDeal: " + damageDeal + "\n" +
                "- maxTargetNumber: " + maxTargetNumber + "\n" +
                "- Nombre de cibles attaquées: " + targets.size() + "\n" +
                "- price: " + price;
    }

    /*==================================================================================================================
                                                 GETTEURS/SETTEURS
    ==================================================================================================================*/
    @Override
    public int getCost() {
        return price;
    }

    /**
     * Renvoie la position du centre de la tour
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Renvoie la position du coin supérieur gauche de la tour
     */
    public Position getCornerPosition() {
        return position.getAdded(new Position(-size / 2.0 * map.getTileMetricWidth(), -size / 2.0 * map.getTileMetricWidth(), map));
    }

    /**
     * Changer la position de la tour
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
    public abstract Shop.ShopCases getID();
}
