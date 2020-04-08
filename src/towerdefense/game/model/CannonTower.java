package towerdefense.game.model;

public class CannonTower extends Tower{
    private Position position;

    public CannonTower(Position position){
        this.position = position;
        super.damage = 10;
        super.range = 10;
    }

    @Override
    public void levelUp(GoldMine mine) {

    }
}
