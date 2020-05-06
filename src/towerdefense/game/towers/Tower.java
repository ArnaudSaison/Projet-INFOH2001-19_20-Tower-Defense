package towerdefense.game.towers;

import towerdefense.game.Buyable;
import towerdefense.game.Drawable;
import towerdefense.game.Placeable;
import towerdefense.game.Upgradable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.view.Printable;

import java.util.ArrayList;

public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable, Runnable {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Attributs relatifs au passage de niveau:
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
    protected Position position;
    protected GameModel gameModel;
    protected Thread tTower;
    private Boolean running;

    //TODO: gestion du prix via un fichier properties ?
    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public Tower(Map map, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe){
        this.gameModel = gameModel;

        //Initialisation du niveau:
        this.towerSpe = towerSpe;
        level = 1;
        maxLevel = 3;

        //Initialisation des attributs:
        ArrayList<Integer> level1Spe = towerSpe.get(1);
        setAttributes(level1Spe);
        position = new Position(map);
        targets = new ArrayList<>();

        //Initialisation du thread:
        tTower = new Thread();
        running = false;
    }

    /*==================================================================================================================
                                                TRAITEMENT DES CIBLES
    ==================================================================================================================*/
    /**Test si les ennemis sur la carte sont à portée de tir.*/
    public void targetAcquisition() {
        ArrayList<NPC> npcsOnMap = gameModel.getNPCsOnMap();
        if (targets.size() < maxTargetNumber) {
            for (NPC npc: npcsOnMap) {
                if ((npc.getPos()).getDistance(position) <= range)
                    targets.add(npc);
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
        return level<maxLevel;
    }

    @Override
    public void levelUp() {
        if(canBeLeveledUp()) {
            level++;
            ArrayList<Integer> levelSpe = towerSpe.get(level-1);
            setAttributes(levelSpe);
        }
    }

    private void setAttributes(ArrayList<Integer> levelSpe){
        price = levelSpe.get(0);
        range = levelSpe.get(1);
        fireRate = levelSpe.get(2);
        damageDeal = levelSpe.get(3);
        maxTargetNumber = levelSpe.get(4);
    }

    /*==================================================================================================================
                                                     GESTION DU THREAD
    ==================================================================================================================*/
    public void initialize(){
        running = true;
        tTower.start();
    }

    @Override
    public void run(){
        while (running) {
            while (!gameModel.getPaused()) {
                try {
                    attack();
                    tTower.sleep(1000/fireRate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        while (gameModel.getPaused()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*==================================================================================================================
                                                    JavaFX
    ==================================================================================================================*/
    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    public void initDrawing() {
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    public void updateDrawing() {
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    public Printable getDrawing() {
        return null;
    }

    /*==================================================================================================================
                                                    AUTRES
    ==================================================================================================================*/
    @Override
    public String toString(){
        return "Tour :\n - position: " + position + "\n" +
                "- level: " + level + "\n"+
                "- maxLevel: " + maxLevel + "\n"+
                "- range: " + range + "\n"+
                "- fireRate: " + fireRate + "\n"+
                "- damageDeal: " + damageDeal + "\n"+
                "- maxTargetNumber: " + maxTargetNumber + "\n"+
                "- Nombre de cibles attaquées: " + targets.size() + "\n"+
                "- price: " + price;
    }

    /*==================================================================================================================
                                                 GETTEURS/SETTEURS
    ==================================================================================================================*/
    @Override
    public int getCost(){
        return price;
    }

    public Position getPos(){
        return position;
    }

    @Override
    public void setPosition(Position position){
        this.position = position;
    }
}
