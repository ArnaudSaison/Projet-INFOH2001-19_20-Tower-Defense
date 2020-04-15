package towerdefense.game.interfaces;

import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.towers.Tower;

public interface Upgradable {

    public int getUpgradeLevel();

    public void levelUp(GoldMine mine);

    public void levelUp(Tower tower);

    //Fonctionne mais Goldmine implémente levelUp(Tower tower) alors que ça ne lui sert à rien ...

    public int getNextUpgradePrice();

    public boolean canBeLeveledUp();

    //public int getUpgradeLevel(); ?
}
