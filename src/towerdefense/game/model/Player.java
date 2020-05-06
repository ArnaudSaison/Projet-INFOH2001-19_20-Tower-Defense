package towerdefense.game.model;

public class Player {

    private int health;
    private int maxHealth;
    private int gold;
    private int score;
    private Thread tPlayer;

    public Player(int initialGold, int initialHealth){
        health = initialHealth;
        gold = initialGold;
        score = 0;
        tPlayer = new Thread();
        tPlayer.start();
        this.health = initialHealth;
        this.maxHealth = initialHealth;
    }


    //******Gestion du score*******


    //******Gestion de l'or*******
    public void increaseGold(int goldIncrement){gold += goldIncrement;}

    public void decreaseGold(int goldDecrement){gold -= goldDecrement;}

    //******Gestion du score*******
    public void increaseHealth(int scoreIncrement){ health += scoreIncrement;}

    public void decreaseHealth(int scoreDecrement){ health -= scoreDecrement;}

    //********Getteurs*********
    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
