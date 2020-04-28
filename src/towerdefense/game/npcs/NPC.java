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


    //Même remarque que pour la classe "Tower": une classe abstraite ne s'instancie pas.
    public NPC (){
        health = 40;//..............en point de vie.
        goldLoot = 1;//.............en pièce d'or.
        speed = 10000;//................en milliseconde.
        //Initilisation du thread
        tNPC = new Thread(this);
        tNPC.start();
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
}