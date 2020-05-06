package towerdefense.game.npcs;

import towerdefense.game.Drawable;
import towerdefense.game.Hittable;
import towerdefense.game.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.map.Tile;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;
import towerdefense.view.Printable;

public abstract class NPC implements Drawable, Movable, Runnable, Hittable {
    //TODO: arriver au bout du chemin ! + (joueur).
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    protected Position position;
    protected GameModel gameModel;
    protected Thread tNPC;
    protected Boolean running;

    //Permet de savoir si le NPC est sur la carte:
    protected boolean onMap;

    //Permet de savoir si le NPC est arrivé au bout du chemin, donc sans se faire tuer:
    protected boolean isArrived;

    //Attributs de spécification:
    protected int health;
    protected int speed;
    protected int goldLoot;
    protected int healthLoot;

    //Optionnel:
    //protected ArrayList<Weapon> inventaire ; (optionnel)

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public NPC (Map map, GameModel gameModel, int health, int speed, int goldLoot, int scoreLoot, Tile gatePathTile){
        position = gatePathTile.getPosition();
        onMap = false;
        isArrived = false;
        this.gameModel = gameModel;
        this.tNPC = new Thread();

        this.health = health;
        this.speed = speed;
        this.goldLoot = goldLoot;
        this.healthLoot= scoreLoot;

        running= false;

    }

    /*==================================================================================================================
                                                        GESTION DES ATTAQUES
    ==================================================================================================================*/
    /**Methode surchargée qui prend en argument un objet type Projectile*/
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
    public abstract void stick(Glue glue);

    public abstract void injure(Shell shell);

    public abstract void pierce(Arrow arrow);

    public void decreaseHealth(int damage){
        if (health <= 0) {
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
        while(onMap && gameModel.getRunning()){
    }
    }

    //todo : removeElementsOnMap(Drawable element)

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/
    @Override
    public Printable getDrawing(){return null;}

    @Override
    public void removeDrawing(){}

    @Override
    public void updateDrawing(){}

    /*==================================================================================================================
                                                        GESTION DU DEPLACEMENT
    ==================================================================================================================*/
    @Override
    public void move(int numberFPS){
    }

    /*==================================================================================================================
                                                        GETTEURS/SETTEURS
    ==================================================================================================================*/
    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

    public int getHealthLoot(){return healthLoot;}

    public boolean getIsArrived(){return isArrived;}

    public void setIsArrived(boolean isArrived){ this.isArrived = isArrived;}

    public void setOnMap(boolean onMap){this.onMap = onMap;}

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