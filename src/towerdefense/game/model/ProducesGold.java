package towerdefense.game.model;

public interface ProducesGold {
    final int gold = 1;

    public void produceGold();

    public int getProductionRate();

    public void increaseProductionRate(int increment);

    //public int retrieveGold(); ?
}
