package towerdefense.game;


import towerdefense.game.model.Shop;

public interface Upgradable {
    public void levelUp();
    public int getLevel();
    public int getMaxLevel();
    public boolean canBeLeveledUp();
    public Shop.ShopCases getID();
}
