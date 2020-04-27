/*
========================================================================================================================
                         COMMENTAIRES SUR LE CODAGE DES CLASSES DU PACKAGE TOWER
========================================================================================================================

Ce package comporte une classe mère "Tower" abstraite qui contient les méthodes et attributs  communs des différentes
sous-classes.
L'idée est de pouvoir modifier les attributs mis en avant dans chacune des sous-classe via le constructeur associé (voir
la classe "Shop").
*/

package towerdefense.game.towers;

import towerdefense.game.Buyable;
import towerdefense.game.Drawable;
import towerdefense.game.Placeable;
import towerdefense.game.Upgradable;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;
import java.util.Iterator;


public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable{
    protected Position position;
    protected int level;
    static int maxLevel;
    protected int price;
    protected int priceIncrement;
    protected double range;
    protected int fireRate;
    protected int damageDeal;
    protected ArrayList<NPC> targets;
    protected ArrayList<NPC> KIATargets;
    protected int maxTargetNumber;
    //private int health; (optionnel)

    //TODO: lancer les treads

    /*
    Une classe abstraite ne peut jamais être instanciée directement; ici, impossible d'instancier une position car
    cela nécessite le passage d'une carte comme argument.
    Ici, les attributs prennent les valeurs de base que toutes tours possèdent au minimum.
    */

    public Tower(){
        level = 1;
        price = 100;
        priceIncrement = 3*level;
        range = 10;//........................en mètre.
        fireRate = 5;//......................coups/seconde.
        damageDeal = 2;//....................en point de vie.
        targets = new ArrayList<NPC>();
        KIATargets = new ArrayList<NPC>();
        maxTargetNumber = 1;//...............traite une seule cible.
    }

    //******Getteurs******

    public int getLevel(){return level;};
    @Override
    public int getCost(){return price;}

    public double getRange(){return range;}

    public int getDamageDeal(){return damageDeal;}
    @Override
    public Position getPos(){return position;}


    //*******Passage de niveau*******
    @Override
    public boolean canBeLeveledUp(){
        return level < maxLevel;
    }

    @Override
    public void levelUp(){
        level++;
        price += priceIncrement;
    }

    //******Traitement des cibles*******

    /*
    * Pas besoin de la méthode getNPC() comme dans le diagramme de séquence car la liste des NPCs (provenant du GameModel)
    * est directement passée en argument.
    * */


    /**Parcourt la liste des NPCS, donnée en argument, tant que la liste des cibles n'est pas remplie, le npc est ajouté à la liste targets
     * (attribut de chaque tour) si il est a porté de tir.
     **/
    public void targetAcquisition(ArrayList<NPC> npcs) {
        Iterator<NPC> itr = npcs.iterator();
        for (NPC npc : npcs){                                          //Pas besoin de condition sur la lecture de la liste : le for la lit entièrement d'office.
            while (targets.size() < maxTargetNumber) {                 // /!\ BOUCLE infinie ????
                if ((npc.getPos()).getDistance(position) <= range){
                        targets.add(npc);
                }
            }
        }
    }

    /**Permet à une tour, peut importe son type, d'attaquer n'importe quel ennemi.**/
    //*******Attaque*******
    public abstract void attack();


    //*******Autres*******
    @Override
    public void updateDrawing(){}

    @Override
    public String toString(){
        return "Tour :\n - position: " + position + "\n" +
                "- level: " + level + "\n"+
                "- maxLevel: " + maxLevel + "\n"+
                "- range: " + range + "\n"+
                "- fireRate: " + fireRate + "\n"+
                "- damageDeal: " + damageDeal + "\n"+
                "- maxTargetNumber: " + maxTargetNumber + "\n"+
                "- Nombre de cibles attaquées: " + targets.size() + "\n"+
                "- price: " + price + ".";
    }

}