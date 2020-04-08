package towerdefense.game.model;

public interface UpgradeLevel {

    public int getLevel();

    public void levelUp(GoldMine mine);

    public void levelUp(Tower tower);

    public boolean canBeLevelUp(int maxLevel);

    //public int getUpgradeLevel(); ?
}
