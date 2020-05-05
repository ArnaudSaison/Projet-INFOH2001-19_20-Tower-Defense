package towerdefense.game.npcs;

import towerdefense.game.Drawable;
import towerdefense.game.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public abstract class NPC implements Drawable, Movable, Runnable {
    //TODO: arriver au bout du chemin ! + (joueur).
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    protected Position position;
    protected GameModel gameModel;
    protected Thread tNPC;
    protected Boolean onMap;
    protected Boolean isArrived;

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
    public NPC (Map map, GameModel gameModel, int health, int speed, int goldLoot, int scoreLoot){
        position = new Position(map);
        onMap = false;
        isArrived = false;
        this.gameModel = gameModel;
        this.tNPC = new Thread();

        this.health = health;
        this.speed = speed;
        this.goldLoot = goldLoot;
        this.healthLoot= scoreLoot;

    }

    /*==================================================================================================================
                                                        GESTION DES ATTAQUES
    ==================================================================================================================*/
    /**Methode surchargée qui prend en argument un objet type Projectile*/
    public void hit(Shell shell){
        explode(shell);
    }

    public void hit(Glue glue){
        stick(glue);
    }

    public void hit(Bullet bullet){
        injure(bullet);
    }

    /*==================================================================================================================
                                                        GESTION DES DEGATS
    ==================================================================================================================*/
    public abstract void stick(Glue glue);

    public abstract void explode(Shell shell);

    public abstract void injure(Bullet bullet);

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
            tNPC.start();
            System.out.println("NPC : je suis initialisé.");
        }
    }

    @Override
    public void run(){}

    @Override
    public void updateDrawing(){}

    /*==================================================================================================================
                                                        GESTION DU DEPLACEMENT
    ==================================================================================================================*/
    @Override
    public void move(){}

    /*==================================================================================================================
                                                        GETTEURS/SETTEURS
    ==================================================================================================================*/
    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

    public int getHealthLoot(){return healthLoot;}

    public Boolean getIsArrived(){return isArrived;}

    public void setOnMap(Boolean onMap){this.onMap = onMap;}

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