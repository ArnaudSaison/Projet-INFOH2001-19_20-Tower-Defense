package towerdefense.game.interfaces;

public interface ProducesGold {
    final int gold = 1;

    public void produceGold();

    public int getProductionRate();

    public int retrieveGold();

    //public int retrieveGold(); ?
}
