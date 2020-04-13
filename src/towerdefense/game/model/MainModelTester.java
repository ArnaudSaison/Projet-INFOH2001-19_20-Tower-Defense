package towerdefense.game.model;

import towerdefense.game.map.Position;
import towerdefense.game.model.npcs.Generic;
import towerdefense.game.model.towers.ArcherTower;

public class MainModelTester {
    public static void main(String args[]) {
        Position p = new Position(1.5,2.53);


        //Test Mine d'or :
        GoldMine mine = new GoldMine();
        mine.setPosition(p);
        mine.levelUp(mine);
        System.out.println("level: " + mine.getLevel() + " price: " + mine.getPrice() + " productionrate :" +  mine.getProductionRate() + " maxGold: " + mine.getMaxGoldStorage() + " gold : " + mine.getGoldStorage());
        mine.produceGold();
        System.out.println("level: " + mine.getLevel() + " price: " + mine.getPrice() + " productionrate: " +  mine.getProductionRate() + " maxGold: " + mine.getMaxGoldStorage() + " gold: " + mine.getGoldStorage());

        //Test tour :
        ArcherTower archerTower = new ArcherTower();
        archerTower.levelUp(archerTower);
        System.out.println("level: " + archerTower.getLevel() + " price: " + archerTower.getPrice() + " damage: " +  archerTower.getDamage() + " maxTarget: " + archerTower.getMaxTargetNumber() + " range: " + archerTower.getRange() + " firerate: " + archerTower.getFireRate());
        //archerTower.setTargets();

        //Test ennemi :
        Generic enemy = new Generic();
        Generic enemy1 = new Generic();
        //System.out.println("Health " + enemy.getHealth() + " loot: " + enemy.getLoot());
        //enemy.hit(45);
        //System.out.println("Health " + enemy.getHealth() + " loot: " + enemy.getLoot());
        //enemy1.hit(4);
        //System.out.println("Health " + enemy1.getHealth() + " loot: " + enemy1.getLoot());

        //test tour attaque ennemis:


        archerTower.getTargets();
        archerTower.dealDamage(5);
        System.out.println("Health enemy  " + enemy.getHealth());
        System.out.println("Health enemy1 " + enemy1.getHealth());
        archerTower.dealDamage(45);
        System.out.println("Health enemy  " + enemy.getHealth());
        System.out.println("Health enemy1 " + enemy1.getHealth());
        archerTower.getTargets();
        System.out.println(enemy);
        System.out.println(enemy1);
    }
}
