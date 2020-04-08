package towerdefense.game.model;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Tower implements Buyable, DealsDamage, UpgradeLevel {
    static int level;
    protected int price;
    protected double range;
    protected int fireRate;
    protected int damage;
    protected ArrayList<NPC> targets;
    protected int maxTargetNumber;
    //private int health; (optionnel)

    public Tower(){
        level = 1;
        price = 10;
        range = 3; //en mètre.
        fireRate = 3;// coups/seconde
        damage = 1;// en point de vie
        targets = new ArrayList<NPC>();
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

    //******Setteurs******

    public void setTargets(NPC target){targets.add(target);}

    //******Autres*****

    public void increasePrice(int increment) {price += increment;}

    public void dealDamage(int damage){
        for (NPC target : targets){
            target.hit(damage);
            if (target.getHealth() == 0){
                targets.remove(target);
                }
            }
        }

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
