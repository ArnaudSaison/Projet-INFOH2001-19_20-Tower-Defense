package towerdefense.game.model;

public class CannonTower extends Tower{

    public CannonTower(){
        super.damage = 10;
        super.range = 10;
    }

    @Override
    public void levelUp(GoldMine mine) {

    }
}
