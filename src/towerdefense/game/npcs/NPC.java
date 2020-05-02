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
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    protected Position position;
    protected GameModel gameModel;
    protected Thread tNPC;

    //Attributs de spécification:
    protected int health;
    protected int goldLoot;
    protected int speed;

    //Optionnel:
    //protected ArrayList<Weapon> inventaire ; (optionnel)

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public NPC (Map map, GameModel gameModel, int health, int speed, int goldLoot){
        position = new Position(map);
        this.gameModel = gameModel;
        this.tNPC = new Thread();

        this.health = health;
        this.goldLoot = goldLoot;
        this.speed = speed;
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
        tNPC.start();
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
                                                        GETTEURS
    ==================================================================================================================*/
    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

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