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

    public NPC (){
        health =30;
        goldLoot = 1;
    }

    //******Getteurs******

    public int getHealth(){return health;}

    public Position getPos(){}

    public int getGoldLoot(){return goldLoot;}

    public boolean getDetected(){}

    public float getSpeed(){return speed;}

    //******Déplacement*******

    public Path pathFinding(){}

    public void move(){}

    //*******Gestion de la vie******

    public void decreaseHealth(){}

    //*******Autres*******

    public void run(){}

    public void updateDrawing(){}

    public String toString(){}
}
