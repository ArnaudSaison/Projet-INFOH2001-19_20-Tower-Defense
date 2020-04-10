package towerdefense.game.model;

public class ArcherTower extends Tower{

    public ArcherTower(){
        super.damage = 7;
        super.range = 12;
    }

    @Override
    public void levelUp(GoldMine mine) {

    }
}
