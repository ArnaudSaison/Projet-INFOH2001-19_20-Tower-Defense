package towerdefense.game.npcs;

import towerdefense.game.interfaces.Drawable;
import towerdefense.game.interfaces.Lootable;
import towerdefense.game.interfaces.Movable;
import towerdefense.game.map.Position;

public abstract class NPC implements Lootable, Drawable, Movable {
    protected Position position;
    protected int health;
    protected int goldLoot;
    protected float speed;
    //protected ArrayList<Weapon> inventaire ; (optionnel)

    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public NPC (){}

    //******Getteurs******

    public int getHealth(){return health;}

    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

    public float getSpeed(){return speed;}

    public abstract String getType();

    //******Déplacement*******



    ///TODO : pathfinding

    public void move(){}

    public void glued(){
        speed = speed/4;
    }

    //*******Gestion de la vie******

    public String decreaseHealth(int damage){
        String res = "alive";
        if (health <= 0) {
            res = "is dead";
        } else {
            health -= damage;
        }
        return res;
    }



    //*******Autres*******
    public void updateDrawing(){}

    public String toString(){
        return "NPC :\n - position: " + position + "\n" +
                "- health: " + health + "\n"+
                "- goldLoot: " + goldLoot + "\n"+
                "- speed: " + speed + ".";
    }
}
