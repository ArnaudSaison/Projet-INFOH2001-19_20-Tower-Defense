package towerdefense.game.model;

public class Player {

    private int health;
    private int gold;

    public Player(int initialGold, int initialHealth){
        health = initialHealth;
        gold = initialGold;
    }

    //******Gestion de l'or*******
    public void increaseGold(int goldIncrement){gold += goldIncrement;}

    public void decreaseGold(int goldDecrement){gold += goldDecrement;}

    //******Gestion du score*******
    public void increaseHealth(int scoreIncrement){ health += scoreIncrement;}

    public void decreaseHealth(int scoreDecrement){ health += scoreDecrement;}

    //********Getteurs*********
    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }
}
