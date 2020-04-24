package towerdefense.game.model;

public class Player {

    private int score;
    private int gold;

    public Player(){
        score = 2000;
        gold = 100;
    }

    //******Gestion de l'or*******

    public void pay(){}

    public void renitializeGold(){}

    public void addGold(int gold){
        this.gold += gold;
    }

    //******Gestion du score*******

    public void increaseScore(){}

    public void decreaseScore(){}

}
