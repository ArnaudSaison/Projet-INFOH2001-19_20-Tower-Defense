package towerdefense.game.model.npcs;

import towerdefense.game.map.Position;
import towerdefense.game.model.interfaces.Drawable;
import towerdefense.game.model.interfaces.Lootable;
import towerdefense.game.model.interfaces.Placeable;
import towerdefense.game.model.position.PathFinding;
import towerdefense.game.model.towers.Tower;

public abstract class NPC implements Lootable, Drawable, Placeable {
    protected Position position;
    protected int health;
    private int goldLoot;
    protected float speed;
    //protected ArrayList<Weapon> inventaire ; (optionnel)

    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public NPC (){
        Position position = new Position;
        health =30;
        goldLoot = 1;
        speed = 2;
    }

    //******Getteurs******

    public int getHealth(){return health;}

    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

    public float getSpeed(){return speed;}

    //******Déplacement*******

    public Path pathFinding(){}

    public void move(){}

    //*******Gestion de la vie******

    public String decreaseHealth(int damage){
        String res = "still alive";
        if (health <= 0) {
            res = "is dead";
        } else {
            health -= damage;
        }
        return res;
    }

    //*******Autres*******

    public void run(){}

    public void updateDrawing(){}

    public String toString(){
        return "NPC :\n - position: " + position + "\n" +
                "- health: " + health + "\n"+
                "- goldLoot: " + goldLoot + "\n"+
                "- speed: " + speed + ".";
    }
}
