package towerdefense.game.npcs;

import towerdefense.game.interfaces.Drawable;
import towerdefense.game.interfaces.Lootable;
import towerdefense.game.interfaces.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public abstract class NPC implements Lootable, Drawable, Movable {
    protected Position position;
    protected int health;
    protected int goldLoot;
    protected float speed;
    //protected ArrayList<Weapon> inventaire ; (optionnel)

    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public NPC (Map map, int health, int goldloot, int speed){ // TODO: ajouter référence au gameModel
        Position position = new Position(map);
        this.health = health;
        this.goldLoot = goldloot;
        this.speed = speed;
    }

    //******Getteurs******

    public int getHealth(){return health;}

    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

    public float getSpeed(){return speed;}

    public abstract String getType(); // TODO: utiliser une récupération d'instance

    //******Déplacement*******



    ///TODO : pathfinding

    public void move(){}

    public void glued(){
        speed = speed/4; // TODO: if pour vérifier su glueresistant
    } // TODO: utiliser verbe + utiliser arguments

    //*******Gestion de la vie******
    // TODO: rajouter méthode hit car la tour ne sait pas ce qui va arriver au NPC
    public String decreaseHealth(int damage){
        String res = "alive"; // TODO: utiliser boolean
        if (health <= 0) {
            res = "is dead";
            gemaModel.killNPC(this);
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
