package towerdefense.game;

import towerdefense.game.map.Position;

public interface Placeable {
    public Position getPos();

    public void setPosition(Position position);
}
