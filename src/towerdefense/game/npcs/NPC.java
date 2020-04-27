/*
========================================================================================================================
                         COMMENTAIRES SUR LE CODAGE DES CLASSES DU PACKAGE NPC
========================================================================================================================

De même que pour les sous classes de la super classe "TOWER", la création de sous classes "NPC" est artificielle et se
justifie par l'implémentation de "l'image" au sein même de la classe.
L'idée est de pouvoir modifier les attributs mis en avant dans chacune des sous-classe via le constructeur associé (voir
la classe "NPCFactory").
*/


package towerdefense.game.npcs;

import towerdefense.game.Drawable;
import towerdefense.game.Movable;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.towers.CanonTower;
import towerdefense.game.towers.GlueTower;

public abstract class NPC implements Drawable, Movable {
    protected Position position;
    protected int health;
    protected int goldLoot;
    protected float speed;
    protected GameModel gameModel;
    //protected ArrayList<Weapon> inventaire ; (optionnel)


    //Même remarque que pour la classe "Tower": une classe abstraite ne s'instancie pas.
    public NPC (){
        health = 40;//..............en point de vie.
        goldLoot = 1;//.............en pièce d'or.
        speed = 1;//................en mètre par seconde.
    }

    //******Getteurs******

    public int getHealth(){return health;}

    public Position getPos(){return position;}

    public int getGoldLoot(){return goldLoot;}

    public float getSpeed(){return speed;}

    //******Gestion des attaques*******

    /**Associe à la tour qui attaque le type de dégâts que subit le NPC**/
    public void hit(Class<?> objectClass, int damageDeal){
        if (objectClass == GlueTower.class){
            glue(damageDeal);
        }else if (objectClass == CanonTower.class) {
            explode(damageDeal);
        }else{
            decreaseHealth(damageDeal);
        }
    }


    //******Déplacement*******

    public void move(){}

    public void glue(int damageDeal){
        if (getClass() != GlueResistantNPC.class){
            speed = speed/damageDeal;
        }
    }

    //*******Gestion de la vie******

    //Pas nécessaire mais fixe les idées.
    public void explode(int damageDeal){
        if (getClass() != ArmoredNPC.class){
            decreaseHealth(damageDeal);
        }
    }

    public void decreaseHealth(int damageDeal){
        if (health <= 0) {
            gameModel.killNPC(this);
        } else {
            health -= damageDeal;
        }
    }


    //*******Autres*******
    public void updateDrawing(){}

    public String toStringNPC(){
        return "NPC :\n - position: " + position + "\n" +
                "- health: " + health + "\n"+
                "- goldLoot: " + goldLoot + "\n"+
                "- speed: " + speed + ".";
    }
}
