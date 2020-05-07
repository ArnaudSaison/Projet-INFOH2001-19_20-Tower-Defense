package towerdefense.game.npcs;

import towerdefense.game.Drawable;
import towerdefense.game.Hittable;
import towerdefense.game.Movable;
import towerdefense.game.Placeable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.map.Tile;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;
import towerdefense.view.Printable;
import towerdefense.view.npc.NPCView;

public abstract class NPC implements Drawable, Movable, Placeable, Runnable, Hittable {
    //TODO: arriver au bout du chemin ! + (joueur).
    /*==================================================================================================================
                                                     ATTRIBUTS
    ==================================================================================================================*/
    protected Position position;
    protected GameModel gameModel;
    protected Map map;
    protected Thread tNPC;
    protected Boolean running;

    //Permet de savoir si le NPC est sur la carte:
    protected boolean onMap;

    //Permet de savoir si le NPC est arrivé au bout du chemin, donc sans se faire tuer:
    protected boolean isArrived;
    protected HeadedDir isHeaded;

    private double width = 1.0/4.0;
    private double height = 1.0/4.0;

    //Attributs de spécification:
    protected int health;
    private int maxHealth;
    protected int speed;
    protected int goldLoot;
    protected int healthLoot;

    //Optionnel:
    //protected ArrayList<Weapon> inventaire ; (optionnel)

    // JavaFX
    private NPCView npcView;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public NPC (Map map, GameModel gameModel, int health, int speed, int goldLoot, int scoreLoot, Tile gatePathTile){
        position = gatePathTile.getPosition();
        onMap = false; //TODO: set sur false dans le gameModel
        isArrived = false;
        this.gameModel = gameModel;
        this.tNPC = new Thread();
        this.map = map;

        this.maxHealth = health;
        this.health = health;

        this.speed = speed;
        this.goldLoot = goldLoot;
        this.healthLoot = scoreLoot;
        isHeaded = HeadedDir.DOWN;
    }

    /*==================================================================================================================
                                                        GESTION DES ATTAQUES
    ==================================================================================================================*/
    /**
     * Methode surchargée qui prend en argument un objet type Projectile
     * */
    @Override
    public void hit(Shell shell){
        injure(shell);
    }

    @Override
    public void hit(Glue glue){
        stick(glue);
    }

    @Override
    public void hit(Arrow arrow){
        pierce(arrow);
    }

    /*==================================================================================================================
                                                        GESTION DES DEGATS
    ==================================================================================================================*/
    //En fonction du type de projectile, applique un effet sur le NPC:

    /**Si non résistant, ralenti le NPC*/
    public abstract void stick(Glue glue);

    /**Si non résistant, blesse le NPC*/
    public abstract void injure(Shell shell);

    /**Blesse le NPC*/
    public abstract void pierce(Arrow arrow);

    /**Retire de la vie au NPC*/
    public void decreaseHealth(int damage){
        if (health - damage <= 0) {
            gameModel.killNPC(this);
        } else {
            health -= damage;
        }
    }

    /*==================================================================================================================
                                                        GESTION DU THREAD
    ==================================================================================================================*/
    public void initialize(){
        if(onMap){
            running = true;
            tNPC.start();
            System.out.println("NPC : je suis initialisé.");
        }
    }

    @Override
    public void run(){
        try {
            while(gameModel.getRunning()){
                while(!gameModel.getPaused()) {
                    // code
                    Thread.sleep(1000);
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //todo : removeElementsOnMap(Drawable element)

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/
    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    public void initDrawing() {
        npcView = new NPCView(this, map, "generic");
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    public void updateDrawing() {
        npcView.update();
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    public Printable getDrawing() {
        return npcView;
    }

    /*==================================================================================================================
                                                        GESTION DU DEPLACEMENT
    ==================================================================================================================*/
    public void move() {
    }

    /**
     * Donne la direction dans laquelle le NPC se dirige
     * @return direction du NPC
     */
    public HeadedDir getHeadedDir() {
        return isHeaded;
    }

    /*==================================================================================================================
                                                    GETTEURS/SETTEURS
    ==================================================================================================================*/
    public Position getPos() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getGoldLoot() {
        return goldLoot;
    }

    public int getHealthLoot() {
        return healthLoot;
    }

    public boolean getIsArrived() {
        return isArrived;
    }

    public void setIsArrived(boolean isArrived) {
        this.isArrived = isArrived;
    }

    public void setOnMap(boolean onMap){this.onMap = onMap;}

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    /*==================================================================================================================
                                                        AUTRES
    ==================================================================================================================*/
    @Override
    public String toString(){
        return "- position: " + position + "\n" +
                "- health: " + health + "\n"+
                "- goldLoot: " + goldLoot + "\n"+
                "- speed: " + speed + ".";
    }
}