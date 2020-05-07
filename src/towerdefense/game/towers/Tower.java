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
import towerdefense.view.towers.TowerView;

import java.util.ArrayList;

public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable, Runnable {
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
    protected Position position;
    protected GameModel gameModel;
    protected Thread tTower;
    private Boolean running;

    // JavaFX
    private TowerView towerView;
    protected String graphicsName;

    //TODO: gestion du prix via un fichier properties ?
    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    /**Constructeur
     * @param towerSpe [[level1Spec], ..., [levelNSpec]]
     */
    public Tower(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe) {
        this.gameModel = gameModel;
        this.map = map;

        //Initialisation du niveau:
        this.towerSpe = towerSpe;
        level = 1;
        maxLevel = 3;

        //Initialisation des attributs:
        ArrayList<Integer> level1Spe = towerSpe.get(0);
        setAttributes(level1Spe);
        position = pos;
        targets = new ArrayList<>();

        //Initialisation du thread:
        tTower = new Thread(this);
        running = false;
    }

    /*==================================================================================================================
                                                TRAITEMENT DES CIBLES
    ==================================================================================================================*/

    /**
     * Test si les ennemis sur la carte sont à portée de tir.
     */
    public void targetAcquisition() {
        ArrayList<NPC> npcsOnMap = gameModel.getNPCsOnMap();
        if (targets.size() < maxTargetNumber) {
            for (NPC npc : npcsOnMap) {
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
    public boolean canBeLeveledUp(){
        return level < maxLevel;
    }

    /**
     * Si le niveau maximal n'est pas atteint, passe les attributs à leur valeur spécifiée par la liste levelSpe (cf: setAttributes()) */
    public void levelUp(){
        if(canBeLeveledUp()){
            level++;
            ArrayList<Integer> levelSpe = towerSpe.get(level - 1);
            setAttributes(levelSpe);
        }
    }

    /**Passe tous les attributs à leur valeur spécifiée par la liste prise en argument.
     * @param levelSpe [int price, int range, int fireRate, int damageDeal, maxEnemyNumber]
     */
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
    /**Démarre le thread de la tour*/
    public void initialize(){
        tTower.start();
    }
    /**Si le jeu tourne, vérifie sa distance aux NPCs sur la carte, si à porté alors attaque*/
    public void run(){
        while (gameModel.getRunning()) {
            try{
                if (!gameModel.getPaused()) {
                    attack();
                    Thread.sleep(1000/fireRate);
                    System.out.println("==========la tour attaque");
                }else {
                    Thread.sleep(1 / gameModel.getConfig().getModelFrameRate());
                    System.out.println("==========la tour est en pause");
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/

    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    public void initDrawing() {
        towerView = new TowerView(this, map, graphicsName);
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    public void updateDrawing() {
        towerView.update();
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    public Printable getDrawing() {
        return towerView;
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
    public int getCost(){
        return price;
    }

    public Position getPos(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }
}
