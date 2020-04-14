package towerdefense.game.model.interfaces;

import towerdefense.game.model.GoldMine;
import towerdefense.game.model.towers.Tower;

public interface Upgradable {

    public int getUpgradeLevel();

    public int levelUp();

    public int getNextUpgradePrice();

    public boolean canBeLeveledUp();

    //public int getUpgradeLevel(); ?
}
