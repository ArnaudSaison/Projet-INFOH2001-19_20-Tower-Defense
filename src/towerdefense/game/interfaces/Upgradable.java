package towerdefense.game.interfaces;

import towerdefense.game.model.GoldMine;

public interface Upgradable {

    public int getUpgradeLevel();

    public int levelUp();

    public int getNextUpgradePrice();

    public boolean canBeLeveledUp();

    //public int getUpgradeLevel(); ?
}
