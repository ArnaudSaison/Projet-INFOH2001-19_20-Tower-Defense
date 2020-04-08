package towerdefense.game.model;

public class ArcherTower extends Tower{
    private Position position;

    public ArcherTower(Position position){
        this.position = position;
        super.damage = 7;
        super.range = 12;
    }

    @Override
    public void levelUp(GoldMine mine) {

    }
}
