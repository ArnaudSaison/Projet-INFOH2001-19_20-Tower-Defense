package towerdefense.game.model;

import javafx.util.Pair;

import java.util.ArrayList;


public abstract class Tower implements Buyable, DealsDamage, UpgradeLevel {
    protected Position position;
    static int level;
    protected int price;
    protected double range;
    protected int fireRate;
    protected int damage;
    protected ArrayList<NPC> targets;
    protected ArrayList<NPC> potentialTargets;
    protected int maxTargetNumber;
    //private int health; (optionnel)

    public Tower(){
        level = 1;
        price = 10;
        range = 3; //en mètre.
        fireRate = 3;// coups/seconde
        damage = 1;// en point de vie
        targets = new ArrayList<NPC>();
        potentialTargets = new ArrayList<NPC>();
        maxTargetNumber = 5;
    }

    //******Getteurs******

    public int getLevel(){return level;}

    public int getPrice(){return price;}

    public double getRange(){return range;}

    public int getFireRate(){return fireRate;}

    public int getDamage(){return damage;}

    public int getMaxTargetNumber(){return maxTargetNumber;}

    public void getTargets(){
        for (NPC target : targets){
            System.out.println(target);
        }
    }

    public Position getTowerPosition(){return position;}

    //******Traitement des cibles*****

    //
    public void findTargets(NPC potentialTarget){
        if (potentialTarget.getDetected()){
            potentialTargets.add(potentialTarget);
        }
    }

    public void setTargets() {
        if (targets.size() < 5) {
            for (NPC potentialTarget : potentialTargets){
                targets.add(potentialTarget);
            }
        }
    }

    //Cette fonction modélise l'attaque d'une tour : quand la vie d'une cible arrive à 0 ou qu'elle sort du rayon d'action de la tour
    //elle doit être retirée de la liste targets; le NPC doit être tué et si il y en a une, une nouvelle cible doit être ajoutée à la liste targets.
    public void dealDamage(int damage) {
        for (NPC target : targets) {
            target.hit(damage);
            if (target.health == 0){
                targets.remove(target);
            }
        }
    }

    //******Augmentation du niveau******

    public void increasePrice(int increment) {price += increment;}

    public boolean canBeLevelUp(int maxLevel) {
        if (level < maxLevel) {
            return true;
        } else {
            return false;
        }
    }

    public void levelUp(Tower tower) {
        if (tower.canBeLevelUp(5)) {
            level++;
            maxTargetNumber ++;
            damage ++;
            fireRate ++;
            range ++;
            tower.increasePrice(5);
        }
    }




    //public String toString(){}


}
