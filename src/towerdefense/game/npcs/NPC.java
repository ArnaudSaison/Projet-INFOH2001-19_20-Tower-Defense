package towerdefense.game.npcs;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import towerdefense.game.interfaces.Drawable;
import towerdefense.game.interfaces.Lootable;
import towerdefense.game.interfaces.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.Drawable;
import towerdefense.game.Movable;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public abstract class NPC implements Drawable, Movable, Runnable {
    protected Position position;
    protected int health;
    protected int goldLoot;
    protected int speed;
    protected GameModel gameModel;
    protected Thread tNPC;
    //protected ArrayList<Weapon> inventaire ; (optionnel)

    private StackPane shape;
    private StackPane healthBarShape;
    private Image image;

    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public NPC (Map map, int health, int goldloot, int speed){ // TODO: ajouter référence au gameModel
        Position position = new Position(map);
        this.health = health;
        this.goldLoot = goldloot;
        this.speed = speed;

        // JavaFX
        shape = new StackPane();
        healthBarShape = new StackPane();
        shape.getChildren().add();
    }

    //******Getteurs******

    public int getHealth(){return health;}

    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

    public float getSpeed(){return speed;}

    //********Setteur*********

    public void setGameModel(GameModel gameModel){this.gameModel = gameModel;}

    //******Gestion des attaques*******
    public void hit(Shell shell){
        explode(shell);
    }

    public void hit(Glue glue){
        stick(glue);
    }

    public void hit(Bullet bullet){
        injure(bullet);
    }

    //*******Gestion des dégâts*********

    public abstract void stick(Glue glue);

    public abstract void explode(Shell shell);

    public abstract void injure(Bullet bullet);

    public void decreaseHealth(int damage){
        if (health <= 0) {
            gameModel.killNPC(this);
        } else {
            health -= damage;
        }
        return res;
    }

    //******Déplacement*******
    @Override
    public void move(){}

    //*******Autres*******
    @Override
    public void run(){
        try {
            while(true){
                move();
                Thread.sleep(speed);
                System.out.println(toString());
            }
        }catch (Exception e){};
    }

    @Override
    public void updateDrawing(){}

    @Override
    public String toString(){
        return "- position: " + position + "\n" +
                "- health: " + health + "\n"+
                "- goldLoot: " + goldLoot + "\n"+
                "- speed: " + speed + ".";
    }

    // ========== Représentation JavaFX ==========
    public void updateDrawing() {

    }
}
