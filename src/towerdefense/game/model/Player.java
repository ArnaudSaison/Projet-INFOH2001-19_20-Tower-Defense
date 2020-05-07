package towerdefense.game.model;

public class Player {

    private int health;
    private int gold;
    private int score;
    private Thread tPlayer;

    public Player(int initialGold, int initialHealth){
        health = initialHealth;
        gold = initialGold;
        score = 0;
        tPlayer = new Thread();
        tPlayer.start();
    }


    //******Gestion du score*******
    public void increaseScore(){score++;}

    //******Gestion de l'or*******
    public void increaseGold(int goldIncrement){gold += goldIncrement;}

    public void decreaseGold(int goldDecrement){gold -= goldDecrement;}

    //******Gestion du score*******
    public void decreaseHealth(int scoreDecrement){ health -= scoreDecrement;}

    //********Getteurs*********
    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public String toString(){
        return ("Player : \n"
                + "-score: " + score+ "\n"
                + "-gold: " + gold+ "\n"
                + "-health: " + health+ "\n");
    }
}
