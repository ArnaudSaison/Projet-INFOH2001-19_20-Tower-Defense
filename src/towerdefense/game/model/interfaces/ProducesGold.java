package towerdefense.game.model.interfaces;

public interface ProducesGold {
    final int gold = 1;

    public void produceGold();

    public int getProductionRate();

    public int retrievesGold();

    //public int retrieveGold(); ?
}
