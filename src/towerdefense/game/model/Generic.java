package towerdefense.game.model;

public class Generic extends NPC {
    private Position position;

    public Generic(){
        Position position = new Position();
        Path path = new Path();
        super.health = 40;
    }
}
